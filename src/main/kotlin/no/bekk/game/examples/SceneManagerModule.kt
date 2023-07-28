package no.bekk.game.examples

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import no.bekk.game.AppModule
import no.bekk.game.AppRunner
import no.bekk.game.config

fun main() {
    Lwjgl3Application(AppRunner { SceneManagerModule() }, config)
}

/**
 * En example of how you can use multiple modules in one module
 * Clicking F1, F2, F3 or F4 will switch the current module.
 *
 * This can be usefull for instane if you want to make a game
 * with multiple scene (Intro scene, game-scene, highscore-scene) etc.
 * The sceneManager works as a singleton that can be used inside each
 * module to trigger transition to a different scene if needed.
 */
class SceneManagerModule : AppModule {
    override fun update(delta: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            SceneManager.activeModule = PerlinModule()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            SceneManager.activeModule = InputAndTextModule()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            SceneManager.activeModule = MovingGraphicModule()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
            SceneManager.activeModule = BezierModule()
        }

        SceneManager.activeModule.update(delta)
    }

    override fun draw(delta: Float) {
        SceneManager.activeModule.draw(delta)
    }
}

object SceneManager {
    // The current active module, the initialValue here is the initial Scene
    var activeModule: AppModule = PerlinModule()
}