package no.bekk.kordle.shared.dto

import kotlinx.serialization.Serializable

@Serializable
data class OppgaveResponse(
    val oppgaveId: Int,
    val lengde: Int,
)

@Serializable
data class GjettOrdRequest(
    val oppgaveId: Int,
    val ordGjett: String
)


@Serializable
data class GjettResponse(
    val oppgaveId: Int,
    val alleBokstavtreff: List<BokstavTreffDTO>
)

@Serializable
data class BokstavTreffDTO(
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
