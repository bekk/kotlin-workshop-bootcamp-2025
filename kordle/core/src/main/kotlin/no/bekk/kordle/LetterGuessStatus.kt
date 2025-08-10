package no.bekk.kordle

import no.bekk.kordle.shared.dto.BokstavTreffDTO

enum class LetterGuessStatus {
    NOT_GUESSED,
    NOT_IN_WORD,
    WRONG_POSITION,
    CORRECT_POSITION;

    companion object {
        fun fromResponse(response: BokstavTreffDTO): LetterGuessStatus {
            return when {
                !response.erBokstavenIOrdet -> NOT_IN_WORD
                response.erBokstavenPaaRettsted -> CORRECT_POSITION
                else -> WRONG_POSITION
            }
        }
    }
}
