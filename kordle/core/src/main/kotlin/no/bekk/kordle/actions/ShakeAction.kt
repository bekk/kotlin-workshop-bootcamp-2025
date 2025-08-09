package no.bekk.kordle.actions

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor
import kotlin.math.exp
import kotlin.math.sin

class ShakeAction(
    private val amplitude: Float = 10f,
    private val frequency: Float = 25f,
    private val decay: Float = 5f,
    private val duration: Float = 0.5f
) : Action() {

    private var time = 0f
    private var originalX = 0f

    override fun setActor(actor: Actor?) {
        super.setActor(actor)
        if (actor != null) {
            originalX = actor.x
        }
    }

    override fun act(delta: Float): Boolean {
        time += delta
        if (time > duration) {
            actor.x = originalX
            return true
        }

        val offset = sin(time * frequency) * exp(-time * decay) * amplitude
        actor.x = originalX + offset
        return false
    }
}
