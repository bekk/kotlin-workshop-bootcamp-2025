package no.bekk.kordle.server.service

import no.bekk.kordle.server.exceptions.OppgavenEksistererIkkeIDatabasenException
import no.bekk.kordle.server.exceptions.OrdetEksistererAlleredeIDatabasenException
import no.bekk.kordle.server.exceptions.OrdetHarUgyldigLengdeException
import no.bekk.kordle.server.repository.OppgaveRepository
import no.bekk.kordle.shared.dto.BokstavTreff
import no.bekk.kordle.shared.dto.HentFasitResponse
import no.bekk.kordle.shared.dto.Oppgave
import no.bekk.kordle.shared.dto.OppgaveResponse
import org.springframework.stereotype.Service

@Service
class OppgaveService(
    val oppgaveRepository: OppgaveRepository
) {

    fun hentTilfeldigOppgave(): Oppgave {
        val alleOppgaver = oppgaveRepository.hentAlleOppgaver()
        val tilfeldigOppgave = alleOppgaver.random()
        return tilfeldigOppgave
    }

    fun hentAlleOppgaver(): List<OppgaveResponse> {
        return oppgaveRepository.hentAlleOppgaver().map { it.tilOppgaveResponse() }
    }

    // TODO: Vurder om sjekken for eksisterende ord bør gjøres i databasen istedenfor. Kanskje en oppgave i seg selv?
    fun leggTilOrd(ordSomSkalLeggesTil: String): OppgaveResponse {
        val erOrdetGyldigLengde = ordSomSkalLeggesTil.length in 4..6
        if (!erOrdetGyldigLengde) {
            throw OrdetHarUgyldigLengdeException("Ordet '$ordSomSkalLeggesTil' må ha en lengde mellom 4 og 6 tegn.")
        }

        val eksistererOrdetAlleredeIDatabasen = oppgaveRepository
            .hentAlleOppgaver()
            // 'Any' spør om det finnes MINST ett ord i listen som matcher betingelsen. I dette tilfellet om det finnes en oppgave med samme ord som det nye ordet en prøver å legge til.
            .any { oppgaveIDatabasen ->
                val ordTiloppgave = oppgaveIDatabasen.ord
                return@any ordTiloppgave == ordSomSkalLeggesTil
            }

        if (eksistererOrdetAlleredeIDatabasen) {
            throw OrdetEksistererAlleredeIDatabasenException("Ordet '$ordSomSkalLeggesTil' finnes allerede i databasen.")
        }

        val idTilNyopprettetOppgave = oppgaveRepository.leggTilOppgave(
            ord = ordSomSkalLeggesTil,
            lengde = ordSomSkalLeggesTil.length
        )
        return OppgaveResponse(
            oppgaveId = idTilNyopprettetOppgave,
            lengde = ordSomSkalLeggesTil.length
        )
    }

    fun gjettOrd(oppgaveId: Int, ordGjettet: String): List<BokstavTreff> {
        val oppgaveGjettetPaa = oppgaveRepository.hentOppgave(oppgaveId)
        if (oppgaveGjettetPaa == null) {
            throw OppgavenEksistererIkkeIDatabasenException("Oppgaven med ID ${oppgaveId} finnes ikke.")
        }
        if (ordGjettet.length != oppgaveGjettetPaa.ord.length) {
            throw OrdetHarUgyldigLengdeException("Gjettet '${ordGjettet}' er feil lengde for oppgaven. Oppgaven har lengde ${oppgaveGjettetPaa.ord.length} tegn.")
        }
        val bokstavTreff = sjekkBokstavTreff(
            ordIOppgave = oppgaveGjettetPaa.ord,
            ordGjettet = ordGjettet
        )
        return bokstavTreff
    }

    fun hentFasitOrd(oppgaveId: Int): HentFasitResponse {
        val oppgave = oppgaveRepository.hentOppgave(oppgaveId)
        if (oppgave == null) {
            throw OppgavenEksistererIkkeIDatabasenException("Oppgaven med ID $oppgaveId finnes ikke.")
        }
        return HentFasitResponse(
            fasitOrd = oppgave.ord
        )
    }

    private fun sjekkBokstavTreff(
        ordIOppgave: String,
        ordGjettet: String
    ): List<BokstavTreff> {
        val ordIOppgaveListe: MutableList<Char?> = ordIOppgave.lowercase().map { it }.toMutableList()
        val treff = ordGjettet.lowercase().mapIndexed { index, bokstav ->
            val hit = ordIOppgave[index] == bokstav
            if (hit) {
                ordIOppgaveListe[index] = null // Fjerner bokstaven fra ordet for å unngå dobbelttelling
            }
            BokstavTreff(
                plassISekvensen = index,
                bokstavGjettet = bokstav,
                erBokstavenIOrdet = hit,
                erBokstavenPaaRettsted = hit
            )
        }
        treff.forEachIndexed { index, treff ->
            if (treff.erBokstavenPaaRettsted) return@forEachIndexed
            val hitIndex = ordIOppgave.indexOfFirst { it == treff.bokstavGjettet }
            if (hitIndex != -1) {
                treff.erBokstavenIOrdet = true
                ordIOppgaveListe[hitIndex] = null
            }
        }
        return treff
    }
}
