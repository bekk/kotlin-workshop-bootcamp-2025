package no.bekk.kordle.server.service

import no.bekk.kordle.server.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository
) {
    // TODO: Legg inn metoder her
}
