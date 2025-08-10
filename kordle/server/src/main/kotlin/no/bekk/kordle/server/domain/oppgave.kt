package no.bekk.kordle.server.domain

import no.bekk.kordle.shared.dto.BokstavTreffDTO
import no.bekk.kordle.shared.dto.OppgaveResponse


/**
 * Representerer treff på en bokstav i et ord.
 * @param plassISekvensen Indikerer posisjonen til bokstaven i ordet. Null-indeksert.
 * @param bokstavGjettet Bokstaven som ble gjettet.
 * @param erBokstavenIOrdet Indikerer om bokstaven finnes i ordet.
 * @param erBokstavenPaaRettsted Indikerer om bokstaven er på riktig sted i ordet.
 */
data class BokstavTreff(
    val plassISekvensen: Int,
    val bokstavGjettet: Char,
    var erBokstavenIOrdet: Boolean,
    val erBokstavenPaaRettsted: Boolean
){
    fun tilBokstavTreffDTO(): BokstavTreffDTO {
        return BokstavTreffDTO(
            plassISekvensen = this.plassISekvensen,
            bokstavGjettet = this.bokstavGjettet,
            erBokstavenIOrdet = this.erBokstavenIOrdet,
            erBokstavenPaaRettsted = this.erBokstavenPaaRettsted
        )
    }
}


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
