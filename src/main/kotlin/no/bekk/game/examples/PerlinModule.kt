package no.bekk.game.examples

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.graphics.color
import ktx.graphics.use
import no.bekk.game.AppModule
import no.bekk.game.AppRunner
import no.bekk.game.config
import no.bekk.game.globals
import no.bekk.game.utils.math.Perlin
import kotlin.random.Random

fun main() {
    Lwjgl3Application(AppRunner { PerlinModule() }, config)
}

class PerlinModule: AppModule {
    private val shapeRenderer = globals.shapeRenderer
    private val randomness = 5f
    private val xSize = 100
    private val ySize = 150
    private val size = 10f

    private var seed = 500 * Random.nextFloat()

    override fun update(delta: Float) {
        seed += 0.1f * delta
    }

    override fun draw(delta: Float) {
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            (1..xSize).map { y ->
                (1..ySize).map { x ->
                    val noise = Perlin.noiseScaled(
                        x = seed + (x / xSize.toDouble()) * randomness,
                        y = seed + (y / ySize.toDouble()) * randomness,
                        z = 0.5
                    ).toFloat()
                    it.color = color(1f, 1f, 1f, noise)
                    it.rect(size * x, size * y, size, size)
                }
            }
        }
        Gdx.gl.glDisable(GL20.GL_BLEND)
    }
}