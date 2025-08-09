package no.bekk.kordle.shared.dto

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val id: Int,
    val username: String,
)

@Serializable
data class CreateUserRequest(
    val username: String,
)

@Serializable
data class UserOppgaveResult(
    val userId: Int,
    val oppgaveId: Int,
    val success: Boolean,
    val attemptCount: Int,
)

@Serializable
data class StatsForUser(
    val userId: Int,
    val amountOfOppgaverFailed: Int,
    val amountOfOppgaverSolvedByGjett: Map<Int, Int>
)
