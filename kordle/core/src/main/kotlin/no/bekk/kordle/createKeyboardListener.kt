package no.bekk.kordle

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener

interface KeyboardReceiver {
    fun onLetterPressed(letter: Char)
    fun onBackspacePressed()
    fun onEnterPressed()
    fun onEscapePressed()
}

fun createKeyboardListener(receiver: KeyboardReceiver): InputListener {
    return object : InputListener() {
        override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
            when (keycode) {
                Input.Keys.BACKSPACE -> {
                    receiver.onBackspacePressed()
                }

                Input.Keys.ENTER -> {
                    receiver.onEnterPressed()
                }

                Input.Keys.ESCAPE -> {
                    receiver.onEscapePressed()
                }

                else -> {
                    val letter = Input.Keys.toString(keycode)
                    val norwegianLetter = mapOf('\'' to 'æ', ';' to 'ø', '[' to 'å')
                    if (letter.length == 1) {
                        val char = letter[0]
                        if (char in norwegianLetter) {
                            receiver.onLetterPressed(norwegianLetter[char]!!)
                            return super.keyDown(event, keycode)
                        } else if (char in 'A'..'Z') {
                            receiver.onLetterPressed(char.lowercaseChar())
                            return super.keyDown(event, keycode)
                        }
                    }
                }
            }

            return super.keyDown(event, keycode)
        }
    }
}
