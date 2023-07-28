package no.bekk.game.examples

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.graphics.rect
import ktx.graphics.use
import no.bekk.game.AppModule
import no.bekk.game.AppRunner
import no.bekk.game.config
import no.bekk.game.globals
import no.bekk.game.utils.TxSize
import no.bekk.game.utils.getGraphic
import no.bekk.game.utils.x
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    Lwjgl3Application(AppRunner { MovingGraphicModule() }, config)
}
class MovingGraphicModule: AppModule {
    private val spriteBatch = globals.spriteBatch
    private val shapeRenderer = globals.shapeRenderer
    private val tree = getGraphic("graphic/mediumtree.png", TxSize.Grid(0, 0, TxSize.s128))

    override fun update(delta: Float) {
    }

    override fun draw(delta: Float) {
        val xposition = sin(globals.elapsedTime)
        val yposition = cos(globals.elapsedTime)

        spriteBatch.use {
            it.draw(tree, 800f - xposition*50f, 300f + yposition*30f, 300f, 300f)
        }
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            it.color = Color.RED
            it.rect(500f + xposition*100f x 500f, 20f x 20f)
        }

    }
}