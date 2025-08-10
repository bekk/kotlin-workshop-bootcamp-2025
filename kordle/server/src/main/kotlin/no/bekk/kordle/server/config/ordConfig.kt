package no.bekk.kordle.server.config

import no.bekk.kordle.server.domain.Oppgave
import no.bekk.kordle.shared.dto.HentFasitRequest
import no.bekk.kordle.shared.dto.HentFasitResponse
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * OBS: Her trenger du ikke å titte! <3
 * Dette er en klasse som brukes for å hente ut fasitOrdet fra databasen, slik at når du tester så får du opp fasiten.
 * Dessverre er det veldig lite læring i å se på dette, da dette er til en viss grad en fasit på hvordan oppgavene kan løses.
 * Anbefaler å lukke denne filen og hoppe tilbake til oppgavene <3
 */


@RestController
class FasitOrdController(val fasitOrdService: FasitOrdService) {

    @PostMapping("/hentFasit")
    fun hentFasit(@RequestBody hentFasitRequest: HentFasitRequest): HentFasitResponse {
        return HentFasitResponse(
            fasitOrd = fasitOrdService.hentFasitOrd(hentFasitRequest.oppgaveId)
        )
    }
}


@Service
class FasitOrdService(
    val fasitOrdRepository: FasitOrdRepository
) {
    fun hentFasitOrd(oppgaveId: Int): String {
        val oppgave = fasitOrdRepository.hentOppgave(oppgaveId)
        return oppgave.ord
    }
}

@Repository
class FasitOrdRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {
    fun hentOppgave(oppgaveId: Int): Oppgave {
        return jdbcTemplate.query(
            """
                |SELECT * FROM OPPGAVE
                |WHERE ID = :id
            """.trimMargin(),
            MapSqlParameterSource(
                mapOf(
                    "id" to oppgaveId,
                )
            ),
            DataClassRowMapper(Oppgave::class.java)
        ).first()
    }
}
