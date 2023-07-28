package no.bekk.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import no.bekk.game.EngineConfig
import no.bekk.game.globals

/**
 * Scales a click based on the window size.
 *
 * Scales based on FitViewport that includes a gutter.
 */
fun scaledPointer(): Vector2 {
    val camera = globals.camera
    val cx = camera.position.x
    val cy = camera.position.y

    val gameWidth = EngineConfig.VIEWPORT_WIDTH
    val gameHeight = EngineConfig.VIEWPORT_HEIGHT

    val (x, y) = basePointer(camera.zoom)

    val rx = x + cx - (gameWidth / 2f) * camera.zoom
    val ry = y + cy - (gameHeight / 2f) * camera.zoom

    return (rx x ry)
}

private fun basePointer(zoom: Float = 1f): Vector2 {
    val gutterWidth = EngineConfig.gutterWidth
    val gutterHeight = EngineConfig.gutterHeight

    val screenWidth = EngineConfig.width
    val screenHeight = EngineConfig.height

    val gameWidth = EngineConfig.VIEWPORT_WIDTH
    val gameHeight = EngineConfig.VIEWPORT_HEIGHT

    val x = (Gdx.input.x - gutterWidth / 2) * (gameWidth / (screenWidth - gutterWidth) * zoom)
    val y = (screenHeight - Gdx.input.y - gutterHeight / 2) * (gameHeight / (screenHeight - gutterHeight) * zoom)

    return Vector2(x, y)
}
