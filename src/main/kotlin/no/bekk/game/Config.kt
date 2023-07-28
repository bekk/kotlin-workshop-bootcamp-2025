package no.bekk.game

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

object EngineConfig {
    const val VIEWPORT_WIDTH = 1920f
    const val VIEWPORT_HEIGHT = 1080f

    var width = 1920 * 2 / 3
    var height = 1080 * 2 / 3
    var gutterHeight: Int = 0
    var gutterWidth: Int = 0
}

val config = Lwjgl3ApplicationConfiguration().apply {
    setTitle("kotlin libgdx intro")
    setWindowedMode(EngineConfig.width, EngineConfig.height)
    setForegroundFPS(60)
    setResizable(false)
    useVsync(false)
}