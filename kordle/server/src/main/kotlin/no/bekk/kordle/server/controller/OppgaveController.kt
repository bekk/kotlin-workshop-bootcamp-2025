package no.bekk.kordle.server.controller

import no.bekk.kordle.server.exceptions.OppgavenEksistererIkkeIDatabasenException
import no.bekk.kordle.server.exceptions.OrdetEksistererAlleredeIDatabasenException
import no.bekk.kordle.server.exceptions.OrdetHarUgyldigLengdeException
import no.bekk.kordle.server.service.OppgaveService
import no.bekk.kordle.server.service.OrdValidatorService
import no.bekk.kordle.shared.dto.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OppgaveController(
    private val oppgaveService: OppgaveService,
    private val ordValidatorService: OrdValidatorService
) {

    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String?> {
        return ResponseEntity.ok().body("Kordle server is running")
    }

    @GetMapping("/hentAlleOppgaver")
    fun hentAlleOppgaver(): ResponseEntity<*> {
        val alleOpppgaver = oppgaveService.hentAlleOppgaver()
        return ResponseEntity.ok().body(alleOpppgaver)
    }

    @GetMapping("/hentTilfeldigOppgave")
    fun hentTilfeldigOppgave(): OppgaveResponse {
        return oppgaveService.hentTilfeldigOppgave().tilOppgaveResponse()
    }

    @PostMapping("/leggTilOrd")
    fun leggTilOrd(@RequestBody leggTilOrdRequest: LeggTilOrdRequest): ResponseEntity<*> {
        try {
            val ordSomSkalLeggesTil = leggTilOrdRequest.ord
            val ordSomBleLagtTil = oppgaveService.leggTilOrd(ordSomSkalLeggesTil)
            return ResponseEntity.ok().body<OppgaveResponse>(ordSomBleLagtTil)

        } catch (exception: RuntimeException) {
            val statusKodeSomSkalReturneres = when (exception) {
                is OrdetEksistererAlleredeIDatabasenException -> HttpStatus.CONFLICT
                is OrdetHarUgyldigLengdeException -> HttpStatus.BAD_REQUEST
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }
            return ResponseEntity
                .status(statusKodeSomSkalReturneres)
                .body(exception.message)
        }
    }

    @PostMapping("/gjettOrd")
    fun gjettOrd(@RequestBody gjettOrdRequest: GjettOrdRequest): GjettResponse {
        val bokstavTreff = oppgaveService.gjettOrd(
            oppgaveId = gjettOrdRequest.oppgaveId,
            ordGjettet = gjettOrdRequest.ordGjett
        )
        val gjettResponse = GjettResponse(
            oppgaveId = gjettOrdRequest.oppgaveId,
            alleBokstavtreff = bokstavTreff
        )
        return gjettResponse
    }

//    @PostMapping("/gjettOrd")
//    fun gjettOrd(@RequestBody gjettOrdRequest: GjettOrdRequest): ResponseEntity<*> {
//        try {
//            if (!ordValidatorService.isValid(gjettOrdRequest.ordGjett)) {
//                throw GjettetErIkkeIOrdlistaException("Ordet '${gjettOrdRequest.ordGjett}' er ikke i ordlista.")
//            }
//            val bokstavTreff = oppgaveService.gjettOrd(
//                oppgaveId = gjettOrdRequest.oppgaveId,
//                ordGjettet = gjettOrdRequest.ordGjett
//            )
//            val gjettResponse = GjettResponse(
//                oppgaveId = gjettOrdRequest.oppgaveId,
//                alleBokstavtreff = bokstavTreff
//            )
//            return ResponseEntity.ok().body(gjettResponse)
//
//        } catch (exception: RuntimeException) {
//            val statusKodeSomSkalReturneres = when (exception) {
//                is GjettetHarUgyldigLengdeException -> HttpStatus.BAD_REQUEST
//                is OppgavenEksistererIkkeIDatabasenException -> HttpStatus.BAD_REQUEST
//                else -> HttpStatus.INTERNAL_SERVER_ERROR
//            }
//            return ResponseEntity
//                .status(statusKodeSomSkalReturneres)
//                .body(exception.message)
//        }
//    }

    @PostMapping("/hentFasit")
    fun hentFasit(@RequestBody hentFasitRequest: HentFasitRequest): ResponseEntity<*> {
        try {
            val fasitOrd = oppgaveService.hentFasitOrd(hentFasitRequest.oppgaveId)
            return ResponseEntity.ok().body(fasitOrd)

        } catch (exception: RuntimeException) {
            val statusKodeSomSkalReturneres = when (exception) {
                is OppgavenEksistererIkkeIDatabasenException -> HttpStatus.BAD_REQUEST
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }
            return ResponseEntity
                .status(statusKodeSomSkalReturneres)
                .body(exception.message)
        }
    }
}



