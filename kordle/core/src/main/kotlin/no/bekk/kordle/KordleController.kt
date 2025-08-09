package no.bekk.kordle

import no.bekk.kordle.requests.gjettOrd
import no.bekk.kordle.shared.dto.GjettOrdRequest
import no.bekk.kordle.shared.dto.OppgaveResponse

class KordleController(private val ui: KordleUI) {
    var value = ""
    var currentOppgave: OppgaveResponse? = null
        set(value) {
            field = value
            if (value != null) {
                ui.processReset()
            }
        }
    val wordLength: Int?
        get() = currentOppgave?.lengde
    val maxGuesses = 6
    var currentGuessIndex: Int = 0
        set(value) {
            field = value
            ui.processSetActiveRow(value)
        }

    fun submit() {
        val oppgave = currentOppgave ?: return
        val gjettOrdRequest = GjettOrdRequest(
            oppgaveId = oppgave.oppgaveId,
            ordGjett = value.uppercase()
        )
        gjettOrd(gjettOrdRequest, { response ->
            ui.processGjett(response)
            val won = response.alleBokstavtreff.all { it.erBokstavenPaaRettsted }
            if (won) {
                ui.processGameOver(true)
            }
            if (currentGuessIndex < maxGuesses - 1) {
                currentGuessIndex++
            } else if (!won) {
                ui.processGameOver(false)
            }

            value = ""
        }, {
            ui.processInvalidGjett(it.message!!)
        })
    }

    fun autoFail() {
        ui.processGameOver(false)
    }

    fun reset() {
        value = ""
        currentGuessIndex = 0
        ui.processReset()
    }

    fun removeLetter() {
        if (value.isNotEmpty()) {
            value = value.dropLast(1)
            ui.processRemoveLetter()
        }
    }

    fun addLetter(letter: Char) {
        val oppgave = currentOppgave ?: return
        if (value.length >= oppgave.lengde) return
        value += letter
        ui.processAddLetter(letter)
    }
}
