package no.bekk.kordle.server.repository

import no.bekk.kordle.shared.dto.Oppgave
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
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
            // TODO: Legg inn SQL-spørring her
            """
                SELECT * FROM OPPGAVE
                """.trimIndent(),
            DataClassRowMapper(Oppgave::class.java),
        )
    }

    /**
     * Legger til et ord som en oppgave i databasen og returnerer ID-en til den nye oppgaven som blir laget.
     * @param ord Ordet til oppgaven som skal legges til.
     * @param lengde Lengden på ordet til oppgaven som skal legges til.
     * @return ID-en til den nye oppgaven som ble lagt til i databasen.
     */
    fun leggTilOppgave(ord: String, lengde: Int): Int {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            """INSERT INTO OPPGAVE (ord, lengde)
                |VALUES (:ord, :lengde)
            """.trimMargin(),
            MapSqlParameterSource(
                mapOf(
                    "ord" to ord,
                    "lengde" to lengde,
                )
            ),
            keyHolder
        )
        return (keyHolder.key as Long).toInt()
    }

    fun eksistererOrdIDatabasen(ord: String): Int? {
        return jdbcTemplate.queryForObject(
            """SELECT CASE WHEN
                |EXISTS(SELECT ord FROM OPPGAVE WHERE ord=:ord)
                |THEN 1
                |ELSE 0
                |END AS ordEksisterer
                |FROM dual;
            """.trimMargin(),
            MapSqlParameterSource(
                mapOf(
                    "ord" to ord,
                )
            ),
            DataClassRowMapper(Int::class.java)
        )
    }

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


