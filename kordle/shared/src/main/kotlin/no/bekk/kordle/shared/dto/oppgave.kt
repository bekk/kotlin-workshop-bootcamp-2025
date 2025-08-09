package no.bekk.kordle.shared.dto

import kotlinx.serialization.Serializable

@Serializable
data class OppgaveResponse(
    val oppgaveId: Int,
    val lengde: Int,
)

data class Oppgave(
    val id: Int,
    val ord: String,
    val lengde: Int,
) {
    fun tilOppgaveResponse(): OppgaveResponse {
        return OppgaveResponse(
            oppgaveId = this.id,
            lengde = this.lengde
        )
    }
}

@Serializable
data class LeggTilOrdRequest(
    val ord: String
)

@Serializable
data class GjettOrdRequest(
    val oppgaveId: Int,
    val ordGjett: String
)


@Serializable
data class GjettResponse(
    val oppgaveId: Int,
    val alleBokstavtreff: List<BokstavTreff>
)

/**
 * Representerer treff på en bokstav i et ord.
 * @param plassISekvensen Indikerer posisjonen til bokstaven i ordet. Null-indeksert.
 * @param bokstavGjettet Bokstaven som ble gjettet.
 * @param erBokstavenIOrdet Indikerer om bokstaven finnes i ordet.
 * @param erBokstavenPaaRettsted Indikerer om bokstaven er på riktig sted i ordet.
 */
@Serializable
data class BokstavTreff(
    val plassISekvensen: Int,
    val bokstavGjettet: Char,
    var erBokstavenIOrdet: Boolean,
    val erBokstavenPaaRettsted: Boolean
)

@Serializable
data class HentFasitRequest(
    val oppgaveId: Int
)


@Serializable
data class HentFasitResponse(
    val fasitOrd: String
)
