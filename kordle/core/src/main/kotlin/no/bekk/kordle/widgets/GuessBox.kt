package no.bekk.kordle.widgets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import ktx.actors.plusAssign
import ktx.scene2d.KTableWidget
import ktx.scene2d.container
import ktx.scene2d.label
import no.bekk.kordle.BekkColors
import no.bekk.kordle.LetterGuessStatus
import no.bekk.kordle.actions.ShakeAction

class GuessBox(parent: KTableWidget, val index: Int) {
    private val label: Label
    private val whiteBackground: TextureRegionDrawable = parent.skin.getDrawable("white") as TextureRegionDrawable
    private val container = parent.container {
        it.width(40f).height(40f).pad(2f)
        isTransform = true
        setSize(40f, 40f)
        setOrigin(Align.center)
        background = whiteBackground.tint(BekkColors.Vann2)

        label = label("", "large") {
            setAlignment(Align.center)
            color = BekkColors.Dag
        }

    }
    private var isShaking = false
    var value: Char? = null
        set(value) {
            field = value
            label.setText(value?.uppercase() ?: "")
        }

    fun setIsActive() {
        container.background = whiteBackground.tint(BekkColors.Vann1)
    }

    fun reset() {
        value = null
        container.background = whiteBackground.tint(BekkColors.Vann2)
    }

    fun shake() {
        if (isShaking) return
        isShaking = true
        container += sequence(
            ShakeAction(amplitude = 20f),
            run(Runnable {
                isShaking = false
            })
        )
    }

    fun setStatus(status: LetterGuessStatus) {
        val color = when (status) {
            LetterGuessStatus.NOT_GUESSED -> return
            LetterGuessStatus.NOT_IN_WORD -> BekkColors.Natt
            LetterGuessStatus.WRONG_POSITION -> BekkColors.Ild1
            LetterGuessStatus.CORRECT_POSITION -> BekkColors.Jord1
        }
        animateToColor(color)
    }

    private fun animateToColor(color: Color) {
        val action = sequence(
            delay(0.1f * index),
            scaleTo(1f, 0f, 0.2f),
            run(Runnable {
                container.background = whiteBackground.tint(color)
            }),
            scaleTo(1f, 1f, 0.2f)
        )
        container += action
    }
}

