package no.bekk.kordle.server.service

import no.bekk.kordle.server.repository.UserOppgaveResultRepository
import no.bekk.kordle.server.repository.UserRepository
import no.bekk.kordle.shared.dto.StatsForUser
import no.bekk.kordle.shared.dto.User
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val userOppgaveResultRepository: UserOppgaveResultRepository) {
    fun getUserByUsername(username: String): User? {
        return userRepository.getUserByUsername(username)
    }

    fun createUser(username: String): User {
        userRepository.createUser(username)
        return userRepository.getUserByUsername(username)
            ?: throw IllegalStateException("User creation failed for username: $username")
    }

    fun registerResult(
        userId: Int,
        oppgaveId: Int,
        success: Boolean,
        guesses: Int
    ): StatsForUser {
        userOppgaveResultRepository.create(userId, oppgaveId, success, guesses)
        return statsForUser(userId)
    }

    fun statsForUser(userId: Int): StatsForUser {
        val resultater = userOppgaveResultRepository.getUserById(userId)
        val oppgaveCountByAttemptCount = resultater
            .filter { it.success }
            .groupBy { it.attemptCount }
            .mapValues { it.value.size }
        val amountOfOppgaverFailed = resultater.count { !it.success }
        return StatsForUser(userId, amountOfOppgaverFailed, oppgaveCountByAttemptCount)
    }
}
