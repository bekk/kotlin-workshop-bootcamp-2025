package no.bekk.kordle.server.repository

import no.bekk.kordle.shared.dto.User
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {
    fun getUserById(userId: Int): User? {
        return jdbcTemplate.query(
            """
                SELECT
                   *
                FROM
                   KordleUser
                WHERE
                   Id = :userId""".trimIndent(),
            mapOf("userId" to userId),
            DataClassRowMapper(User::class.java)
        ).singleOrNull()
    }

    fun getUserByUsername(username: String): User? {
        return jdbcTemplate.query(
            """
                SELECT
                   *
                FROM
                   KordleUser
                WHERE
                   Username = :username""".trimIndent(),
            mapOf("username" to username),
            DataClassRowMapper(User::class.java)
        ).singleOrNull()
    }

    fun createUser(username: String) {
        val sql = """
            INSERT INTO
                KordleUser (Username)
            VALUES
                (:username);
        """.trimIndent()

        jdbcTemplate.update(
            sql,
            mapOf("username" to username)
        )
    }
}
