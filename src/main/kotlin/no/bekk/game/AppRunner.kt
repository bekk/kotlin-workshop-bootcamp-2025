package no.bekk.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxApplicationAdapter
import ktx.app.clearScreen
import ktx.graphics.rect
import ktx.graphics.use
import no.bekk.game.debug.FPSCounter
import no.bekk.game.debug.VisualMouse
import kotlin.system.measureNanoTime

interface AppModule {
    fun update(delta: Float)
    fun draw(delta: Float)
}

data class AppGlobals(
    val camera: OrthographicCamera,
    val viewport: FitViewport,
    val spriteBatch: SpriteBatch,
    val shapeRenderer: ShapeRenderer,
    var elapsedTime: Float = 0f,
)

lateinit var globals: AppGlobals

class AppRunner(val appModule: () -> AppModule) : KtxApplicationAdapter {

    lateinit var module: AppModule
    lateinit var visualMouse: VisualMouse
    lateinit var fpsCounter: FPSCounter

    var displayVisualMouse = false
    var displayFPS = true

    override fun create() {
        val camera = OrthographicCamera(EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT).also {
            it.setToOrtho(false, EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT)
        }
        val viewport = FitViewport(EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT, camera)
        val spriteBatch = SpriteBatch(2000).also {
            it.projectionMatrix.setToOrtho2D(0f, 0f, EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT)
        }
        val shapeRenderer = ShapeRenderer().also {
            it.projectionMatrix.setToOrtho2D(0f, 0f, EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT)
        }
        globals = AppGlobals(
            camera, viewport, spriteBatch, shapeRenderer
        )

        visualMouse = VisualMouse()
        fpsCounter = FPSCounter()
        module = appModule()
    }

    override fun resize(width: Int, height: Int) {
        EngineConfig.width = width
        EngineConfig.height = height
        globals.viewport.update(width, height)
        globals.viewport.update(width, height)
        EngineConfig.gutterHeight = globals.viewport.topGutterHeight + globals.viewport.bottomGutterHeight
        EngineConfig.gutterWidth = globals.viewport.leftGutterWidth + globals.viewport.rightGutterWidth
    }

    override fun render() {
        clearScreen(0.5f, 0.5f, 0.5f)
        val time = measureNanoTime {
            globals.shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
                it.color = Color.BLACK
                it.rect(Vector2(0f, 0f), Vector2(EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT))
            }
            module.update(Gdx.graphics.deltaTime)
            module.draw(Gdx.graphics.deltaTime)
        }
        globals.elapsedTime += Gdx.graphics.deltaTime
        debug(Gdx.graphics.deltaTime, time)

    }

    private fun debug(delta: Float, time: Long) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            displayVisualMouse = !displayVisualMouse
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            displayFPS = !displayFPS
        }

        if (displayFPS) {
            fpsCounter.update(delta, time)
            fpsCounter.draw()
        }
        if (displayVisualMouse) {
            visualMouse.draw()
        }
    }
}
