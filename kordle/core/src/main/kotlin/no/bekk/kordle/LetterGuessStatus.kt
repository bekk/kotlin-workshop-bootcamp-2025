package no.bekk.kordle

import no.bekk.kordle.shared.dto.BokstavTreff

enum class LetterGuessStatus {
    NOT_GUESSED,
    NOT_IN_WORD,
    WRONG_POSITION,
    CORRECT_POSITION;

    companion object {
        fun fromResponse(response: BokstavTreff): LetterGuessStatus {
            return when {
                !response.erBokstavenIOrdet -> LetterGuessStatus.NOT_IN_WORD
                response.erBokstavenPaaRettsted -> LetterGuessStatus.CORRECT_POSITION
                else -> LetterGuessStatus.WRONG_POSITION
            }
        }
    }
}
