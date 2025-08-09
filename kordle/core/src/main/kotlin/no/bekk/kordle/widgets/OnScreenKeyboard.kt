package no.bekk.kordle.widgets

import ktx.actors.onClick
import ktx.scene2d.*
import no.bekk.kordle.BekkColors
import no.bekk.kordle.KordleController
import no.bekk.kordle.LetterGuessStatus

class OnScreenKeyboard(private val parent: KTableWidget, private val controller: KordleController) {
    private val buttonByCharacter = mutableMapOf<Char, KButton>()
    private val bestGuessByCharacter = mutableMapOf<Char, LetterGuessStatus>()

    init {
        val lines = listOf("qwertyuiopå", "asdfghjkløæ", "zxcvbnm")
        lines.forEachIndexed { i, line ->
            // row with 5 buttons
            parent.row()
            parent.table {
                if (i == lines.size - 1) {
                    // Add a spacer for the last row to align with the delete button
                    button {
                        label("✓", "small")
                        color = BekkColors.Vann1
                        onClick {
                            controller.submit()
                        }
                        it.width(40f)
                    }
                }
                line.forEach { letter ->
                    val b = button {
                        label(letter.uppercase(), "small")
                        color = BekkColors.Vann1
                        onClick {
                            controller.addLetter(letter)
                        }
                        it
                            .width(24f).height(40f)
                            .pad(2f)
                    }
                    buttonByCharacter[letter] = b
                }
                if (i == lines.size - 1) {
                    button {
                        label("⌫", "small")
                        color = BekkColors.Vann1

                        onClick {
                            controller.removeLetter()
                        }
                        it.width(40f)
                    }
                }
            }
        }
        parent.table {
            button {
                label("Gi opp", "small")
                color = BekkColors.Ild1

                onClick {
                    controller.autoFail()
                }
                it.width(100f)
            }
        }
    }

    fun reset() {
        bestGuessByCharacter.clear() // Clear the best guesses
        buttonByCharacter.forEach { (_, button) ->
            button.color = BekkColors.Vann1 // Reset button colors
        }
    }

    fun updateBestGuess(letter: Char, guessStatus: LetterGuessStatus) {
        val currentBestStatus = bestGuessByCharacter.getOrDefault(letter, LetterGuessStatus.NOT_GUESSED)
        if (guessStatus > currentBestStatus) {
            bestGuessByCharacter[letter] = guessStatus
            buttonByCharacter[letter]?.apply {
                color = when (guessStatus) {
                    LetterGuessStatus.NOT_IN_WORD -> BekkColors.Natt
                    LetterGuessStatus.WRONG_POSITION -> BekkColors.Ild1
                    LetterGuessStatus.CORRECT_POSITION -> BekkColors.Jord1
                    else -> BekkColors.Vann1
                }
            }
        }
    }
}
