package no.bekk.game

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import ktx.graphics.rect
import ktx.graphics.use
import no.bekk.game.utils.Rectangle

fun main() {
    Lwjgl3Application(AppRunner { DodgeFallingSquares() }, config)
}

class DodgeFallingSquares: AppModule {
    private val shapeRenderer = globals.shapeRenderer
    private var lastBlockSpawnTime = TimeUtils.millis()
    private var player = Rectangle(
        position = Vector2(EngineConfig.VIEWPORT_WIDTH / 2f, 200f),
        size = Vector2(20f, 20f)
    )
    private var blocksToDodge: List<Rectangle> = emptyList()

    /**
     * This function should draw the player in the current position on the screen.
     * Use the utility method `drawRectangle` to draw on the screen.
     */
    private fun drawPlayer() {
        drawRectangle(player, Color.RED)
    }

    /**
     * In this method we will implement player movement.
     * You can check for keypresses using `Gdx.input.isKeyPressed`
     * for instance you can check for UP-arrow: `Gdx.input.isKeyPressed(Input.Keys.UP)`.
     *
     * @param delta The time since last update. This is used to make the movement-speed
     * not dependant of the update-rate.
     */
    private fun handlePlayerMovement(delta: Float) {

    }

    /**
     * Checks if the game should spawn new blocks. You can use the given timeSinceLastBlock
     * to determine wheter to spawn a new block or not.
     *
     * @param timeSinceLastBlock The time since the last block was spawned in milliseconds.
     * @return true if the game should spawn new blocks, false if not
     */
    private fun shouldSpawnNewBlocks(timeSinceLastBlock: Long): Boolean {
        return false
    }

    /**
     * Here we will implement logic for spawning new blocks that the player
     * has to dodge. The blocks should start at top of the screen at a random
     * X-coordinate. The blocks should be added to the list `blocksToDodge`.
     *
     * Keep in mind that most things (like lists) are immutable in Kotlin. So we
     * can't actually add anything to the list. Instead we want to make a new list.
     */
    private fun spawnNewBlock() {

    }

    /**
     * This function should draw all the blocks the player has to dodge on the screen.
     * Use the utility method `drawRectangle` to draw on the screen.
     */
    private fun drawAllBlocksToDodge() {

    }

    /**
     * Moves all the blocks in `blocksToDodge` list.
     *
     * @param delta The time since last update. This is used to make the movement-speed
     * not dependant of the update-rate.
     */
    private fun handleMoveBlocks(delta: Float) {

    }

    /**
     * Checks if the player is colliding with any of the blocks in the `blocksToDodge`-list.
     * `Rectangle` has a utility method `isCollidingWith` that checks if 2 Rectangles are
     * overlapping.
     *
     * @return true if the player is colliding with any blocks, false if not.
     */
    private fun playerIsColliding(): Boolean {
        return false
    }

    /**
     * Removes all the blocks that are out of bounds. Since the blocks start at the top
     * and fall downwards we can remove any blocks that are below the screen.
     */
    private fun removeBlocksOutOfBounds() {

    }

    /**
     * This is called when the player has lost the game. The initial implementation just
     * resets the game to the original state. Maybe you can come up with something more fun?
     * You could have a Score screen that displays how long you lasted etc.
     */
    private fun onGameLost() {
        player = Rectangle(
            position = Vector2(EngineConfig.VIEWPORT_WIDTH / 2f, 200f),
            size = Vector2(20f, 20f)
        )
        blocksToDodge = emptyList()
    }

    /**
     * This is the main-loop of the game. For the workshop you should not need
     * to change this, but rather implement the different methods called here.
     *
     * But if you want you can allways refactor and add as many new features
     * as you want.
     */
    override fun update(delta: Float) {
        handlePlayerMovement(delta)
        if (shouldSpawnNewBlocks(TimeUtils.timeSinceMillis(lastBlockSpawnTime))) {
            lastBlockSpawnTime = TimeUtils.millis()
            spawnNewBlock()
        }

        handleMoveBlocks(delta)
        removeBlocksOutOfBounds()

        if (playerIsColliding()) {
            onGameLost()
        }
    }

    override fun draw(delta: Float) {
        drawPlayer()
        drawAllBlocksToDodge()
    }

    /**
     * Draws a filled rectangle on the screen.
     *
     * @param rectangle The rectangle to draw
     * @param color The color of the rectangle
     */
    private fun drawRectangle(rectangle: Rectangle, color: Color) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            it.color = color
            it.rect(rectangle.position, rectangle.size)
        }
    }
}
