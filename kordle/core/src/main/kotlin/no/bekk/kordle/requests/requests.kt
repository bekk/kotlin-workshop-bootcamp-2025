package no.bekk.kordle.requests

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.net.HttpStatus
import kotlinx.serialization.json.Json
import no.bekk.kordle.exceptions.StatusCodeException
import no.bekk.kordle.shared.dto.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// Callback-based approach
inline fun <reified R> executeRequest(
    request: Net.HttpRequest,
    crossinline onSuccess: (R) -> Unit,
    crossinline onError: (Throwable) -> Unit
) {

    Gdx.net.sendHttpRequest(request, object : Net.HttpResponseListener {
        override fun handleHttpResponse(httpResponse: Net.HttpResponse) {
            val statusCode = httpResponse.status.statusCode
            val responseBody = httpResponse.resultAsString

            if (statusCode == HttpStatus.SC_OK) {
                try {
                    val result: R = when {
                        responseBody.isBlank() && null is R -> null as R
                        responseBody.isBlank() -> throw IllegalStateException("Expected non-empty response for type ${R::class.simpleName}")
                        else -> Json.decodeFromString(responseBody)
                    }
                    onSuccess(result)
                } catch (e: Exception) {
                    println("Failed to parse response: ${e.message}")
                    onError(e)
                }
            } else {
                onError(
                    StatusCodeException(
                        message = "HTTP request failed with status code $statusCode: $responseBody",
                        statusCode = HttpStatus(statusCode),
                        responseBody = responseBody
                    )
                )
            }
        }

        override fun failed(t: Throwable?) {
            val exception = t ?: Exception("Unknown error occurred")
            onError(exception)
        }

        override fun cancelled() {
            println("Request was cancelled")
        }
    })
}

inline fun <reified T> generateHttpRequest(
    method: String,
    path: String,
    body: T,
): Net.HttpRequest {
    return Net.HttpRequest(method).apply {
        this.url = "http://localhost:8080$path"
        if (body != null) {
            this.content = Json.encodeToString(body)
        }
        // Set content type for JSON
        this.setHeader("Content-Type", "application/json")
    }
}

fun generateHttpRequest(
    method: String,
    path: String,
): Net.HttpRequest {
    return Net.HttpRequest(method).apply {
        this.url = "http://localhost:8080$path"
        // Set content type for JSON
        this.setHeader("Content-Type", "application/json")
    }
}

fun getTilfeldigOppgave(
    onSuccess: (OppgaveResponse) -> Unit,
    onFailure: (Throwable) -> Unit
) {
    val request = generateHttpRequest("GET", "/hentTilfeldigOppgave")

    executeRequest<OppgaveResponse>(
        request,
        onSuccess = { response ->
            onSuccess(response)
        },
        onError = { error ->
            if (error is StatusCodeException) {
                printStatusCodeException(
                    methodExecuted = "Klarte ikke å hente tilfeldig oppgave grunnet feilen:\n",
                    request = request,
                    statusCodeException = error
                )
            } else {
                printWithTimeStamp("Klarte ikke å hente ut en tilfeldig oppgave grunnet feilen: '$error'")
            }
            onFailure(error)
        }
    )
}

fun gjettOrd(
    gjettOrdRequest: GjettOrdRequest,
    onSuccess: (GjettResponse) -> Unit,
    onError: (Throwable) -> Unit
) {
    val request = generateHttpRequest("POST", "/gjettOrd", gjettOrdRequest)

    executeRequest<GjettResponse>(
        request,
        onSuccess = { response ->
            onSuccess(response)
        },
        onError = { error ->
            if (error is StatusCodeException) {
                printStatusCodeException(
                    methodExecuted = "Klarte ikke å utføre et gjett på oppgaven med oppgaveId '${gjettOrdRequest.oppgaveId}' og gjettet '${gjettOrdRequest.ordGjett}' grunnet feilen:\n",
                    request = request,
                    statusCodeException = error
                )
            } else {
                printWithTimeStamp("Klarte ikke å gjette på et ord grunnet feilen: '$error'")
            }
            onError(error)
        }
    )
}

fun getUser(
    username: String,
    onSuccess: (User?) -> Unit
) {
    val request = generateHttpRequest("GET", "/users?username=$username")

    executeRequest<User?>(
        request,
        onSuccess = { response ->
            onSuccess(response)
        },
        onError = { error ->
            if (error is StatusCodeException) {
                if (error.statusCode.statusCode != 404) {
                    printStatusCodeException(
                        methodExecuted = "Klarte ikke å hente ut brukeren med brukernavn '${username}' grunnet feilen:\n",
                        request = request,
                        statusCodeException = error
                    )
                }
            } else {
                printWithTimeStamp("Klarte ikke å hente ut bruker grunnet feilen: '$error'")
            }
        }
    )
}

fun createUser(
    createUserRequest: CreateUserRequest, onSuccess: (User) -> Unit
) {
    val request = generateHttpRequest("POST", "/users", createUserRequest)

    executeRequest<User>(
        request,
        onSuccess = { response ->
            onSuccess(response)
        },
        onError = { error ->
            if (error is StatusCodeException) {
                printStatusCodeException(
                    methodExecuted = "Klarte ikke å opprette brukeren med brukernavn '${createUserRequest.username}' grunnet feilen:\n",
                    request = request,
                    statusCodeException = error
                )
            } else {
                printWithTimeStamp("Klarte ikke å lage en bruker grunnet feilen: '$error'")
            }
        }
    )
}

fun registerResult(
    userOppgaveResult: UserOppgaveResult,
    onSuccess: (StatsForUser) -> Unit
) {
    val request = generateHttpRequest("POST", "/result", userOppgaveResult)

    executeRequest<StatsForUser>(
        request,
        onSuccess = { response ->
            onSuccess(response)
        },
        onError = { error ->
            if (error is StatusCodeException) {
                printStatusCodeException(
                    methodExecuted = "Klarte ikke å registrere et resultat på brukeren med brukernavn '${userOppgaveResult.userId}' grunnet feilen:\n",
                    request = request,
                    statusCodeException = error
                )
            }
        }
    )
}

fun getUserStats(
    userId: Int,
    onSuccess: (StatsForUser) -> Unit
) {
    val request = generateHttpRequest("GET", "/users/$userId/stats")

    executeRequest<StatsForUser>(
        request,
        onSuccess = { response ->
            onSuccess(response)
        },
        onError = { error ->
            if (error is StatusCodeException) {
                printStatusCodeException(
                    methodExecuted = "Klarte ikke å hente ut statistikken på en bruker med userId lik '${userId}' grunnet feilen:\n",
                    request = request,
                    statusCodeException = error
                )
            }
        }
    )
}

fun getFasitord(
    oppgaveId: Int,
    onSuccess: (HentFasitResponse) -> Unit,
    onFailure: (Throwable) -> Unit
) {
    val request = generateHttpRequest("POST", "/hentFasit", HentFasitRequest(oppgaveId))

    executeRequest<HentFasitResponse>(
        request,
        onSuccess = { response ->
            onSuccess(response)
        },
        onError = { error ->
            if (error is StatusCodeException) {
                printStatusCodeException(
                    methodExecuted = "Klarte ikke å hente ut fasitordet på oppgaven med oppgaveId lik '${oppgaveId}' grunnet feilen:\n",
                    request = request,
                    statusCodeException = error
                )
            }
            onFailure(error)
        }
    )
}

fun getCurrentTime(): String {
    return DateTimeFormatter
        .ofPattern("HH:mm:ss")
        .withZone(ZoneId.of("Europe/Oslo"))
        .format(Instant.now())
}

fun printWithTimeStamp(message: String) {
    println("[${getCurrentTime()}]: $message")
}

fun printStatusCodeException(
    methodExecuted: String,
    request: Net.HttpRequest,
    statusCodeException: StatusCodeException
) {
    val requestMetadataErrorString =
        methodExecuted + "Et ${request.method}-kall til endepunktet '${request.url}' returnerte statuskoden '${statusCodeException.statusCode.statusCode}'. "
    val errorMessage =
        if (statusCodeException.responseBody != null) requestMetadataErrorString + "Feilmeldingen fra serveren var: '${statusCodeException.responseBody}'" else requestMetadataErrorString
    printWithTimeStamp(errorMessage)
}
