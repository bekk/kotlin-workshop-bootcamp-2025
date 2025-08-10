package no.bekk.kordle.exceptions

import com.badlogic.gdx.net.HttpStatus

class StatusCodeException(message: String? = null, val statusCode: HttpStatus, val responseBody: String?) :
    RuntimeException(message)
