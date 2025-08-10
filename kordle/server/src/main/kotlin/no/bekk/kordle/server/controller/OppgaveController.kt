package no.bekk.kordle.server.controller

import no.bekk.kordle.server.service.OppgaveService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OppgaveController(
    private val oppgaveService: OppgaveService
) {
    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String?> {
        return ResponseEntity.ok().body("Kordle server is running")
    }
}



