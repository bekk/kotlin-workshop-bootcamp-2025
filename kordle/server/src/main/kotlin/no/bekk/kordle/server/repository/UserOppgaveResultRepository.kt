package no.bekk.kordle.server.repository

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserOppgaveResultRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {
    // TODO: Legg inn metoder her
}
