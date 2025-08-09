package no.bekk.kordle.server.controller

import no.bekk.kordle.server.service.UserService
import no.bekk.kordle.shared.dto.CreateUserRequest
import no.bekk.kordle.shared.dto.StatsForUser
import no.bekk.kordle.shared.dto.User
import no.bekk.kordle.shared.dto.UserOppgaveResult
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userService: UserService) {
    @GetMapping("/users")
    fun getOrCreateUser(@RequestParam("username") username: String): User? {
        return userService.getUserByUsername(username)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody body: CreateUserRequest): User {
        return userService.createUser(body.username)
    }

    @PostMapping("/result")
    fun registerUserOppgave(
        @RequestBody body: UserOppgaveResult
    ): StatsForUser {
        return userService.registerResult(body.userId, body.oppgaveId, body.success, body.attemptCount)
    }

    @GetMapping("/users/{userId}/stats")
    fun getUserStats(@PathVariable userId: Int): StatsForUser {
        return userService.statsForUser(userId)
    }
}
