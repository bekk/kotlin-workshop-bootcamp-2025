package no.bekk.kordle.server.repository

import no.bekk.kordle.server.domain.Oppgave
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class OppgaveRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {
    /**
     * Henter alle oppgaver fra databasen. Funksjonen kjører en SQL-spørring som henter alle rader fra OPPGAVE-tabellen
     * og bruker DataClassRowMapper for å mappe resultatet til en liste av Oppgave-objekter.
     * Hvis det ikke finnes noen oppgaver i databasen, returneres en tom liste.
     * Datamapperen 'DataClassRowMapper' brukes for å konvertere hver rad i resultatsettet til et Oppgave-objekt.
     * @return En liste av alle Oppgave-objekter som finnes i databasen.
     */
    fun hentAlleOppgaver(): List<Oppgave> {
        return jdbcTemplate.query(
            """
                <LEGG INN SQL SPØRRINGEN HER>
                """.trimIndent(),
            DataClassRowMapper(Oppgave::class.java),
        )
    }
}


