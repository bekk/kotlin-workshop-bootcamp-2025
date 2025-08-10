package no.bekk.kordle.server.controller

import no.bekk.kordle.server.service.UserService
import no.bekk.kordle.shared.dto.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userService: UserService) {
    @GetMapping("/users")
    fun getUser(@RequestParam("username") username: String): User? {
        return userService.getUserByUsername(username)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody body: CreateUserRequest): User {
        return userService.createUser(body.username)
    }

    @GetMapping("/users/{userId}/stats")
    fun getUserStats(@PathVariable userId: Int): StatsForUser {
        return userService.statsForUser(userId)
    }

    @PostMapping("/result")
    fun registerUserOppgave(
        @RequestBody body: UserOppgaveResult
    ): StatsForUser {
        return userService.registerResult(body.userId, body.oppgaveId, body.success, body.attemptCount)
    }


    @PostMapping("/hentFasit")
    fun hentFasit(@RequestBody hentFasitRequest: HentFasitRequest): ResponseEntity<*> {
        try {
            val fasitOrd = userService.hentFasitOrd(hentFasitRequest.oppgaveId)
            return ResponseEntity.ok().body(fasitOrd)

        } catch (exception: RuntimeException) {
            val statusKodeSomSkalReturneres = when (exception) {
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }
            return ResponseEntity
                .status(statusKodeSomSkalReturneres)
                .body(exception.message)
        }
    }
}
