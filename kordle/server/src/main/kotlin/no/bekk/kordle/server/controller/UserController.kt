package no.bekk.kordle.server.controller

import no.bekk.kordle.server.service.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService) {
    // TODO: Legg inn metoder her
}
