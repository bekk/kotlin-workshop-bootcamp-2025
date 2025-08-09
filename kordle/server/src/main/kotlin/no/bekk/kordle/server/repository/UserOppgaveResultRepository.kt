package no.bekk.kordle.server.repository

import no.bekk.kordle.shared.dto.UserOppgaveResult
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserOppgaveResultRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {
    fun getUserById(userId: Int): List<UserOppgaveResult> {
        return jdbcTemplate.query(
            """
                SELECT
                   *
                FROM
                   UserOppgaveResult
                WHERE
                   UserId = :userId""".trimIndent(),
            mapOf("userId" to userId),
            DataClassRowMapper(UserOppgaveResult::class.java)
        )
    }

    fun create(
        userId: Int,
        oppgaveId: Int,
        success: Boolean,
        guesses: Int
    ) {
        val sql = """
            INSERT INTO
                UserOppgaveResult (UserId, OppgaveId, Success, AttemptCount)
            VALUES
                (:userId, :oppgaveId, :success, :guesses);
        """.trimIndent()

        jdbcTemplate.update(
            sql,
            mapOf(
                "userId" to userId,
                "oppgaveId" to oppgaveId,
                "success" to success,
                "guesses" to guesses
            )
        )
    }
}
