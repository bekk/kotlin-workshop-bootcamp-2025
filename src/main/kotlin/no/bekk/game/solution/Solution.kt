package no.bekk.game.solution

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import ktx.graphics.rect
import ktx.graphics.use
import no.bekk.game.AppModule
import no.bekk.game.AppRunner
import no.bekk.game.EngineConfig
import no.bekk.game.config
import no.bekk.game.globals
import no.bekk.game.utils.Rectangle
import no.bekk.game.utils.x

fun main() {
    Lwjgl3Application(AppRunner { DodgeFallingSquaresSolution() }, config)
}

class DodgeFallingSquaresSolution: AppModule {
    private val shapeRenderer = globals.shapeRenderer
    private var lastBlockSpawnTime = TimeUtils.millis()
    private var movementSpeed = 500f
    private var blocksFallSpeed = 250f
    private var player = Rectangle(
        position = EngineConfig.VIEWPORT_WIDTH / 2 x 200f,
        size = 20 x 20
    )
    private var blocksToDodge: List<Rectangle> = emptyList()

    /**
     * In this method we will implement player movement.
     * You can check for keypresses using `Gdx.input.isKeyPressed`
     * for instance you can check for UP-arrow: `Gdx.input.isKeyPressed(Input.Keys.UP)`.
     *
     * @param delta The time since last update. This is used to make the movement-speed
     * not dependant of the update-rate.
     */
    private fun handlePlayerMovement(delta: Float) {
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.position.x += delta * movementSpeed
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.position.x -= delta * movementSpeed
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.position.y += delta * movementSpeed
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.position.y -= delta * movementSpeed
        }

        if (player.position.x < 0) { player.position.x = 0f }
        if (player.position.x + player.size.x > EngineConfig.VIEWPORT_WIDTH) {
            player.position.x = EngineConfig.VIEWPORT_WIDTH - player.size.x
        }

        if (player.position.y < 0) { player.position.y = 0f }
        if (player.position.y + player.size.y > EngineConfig.VIEWPORT_HEIGHT) {
            player.position.y = EngineConfig.VIEWPORT_HEIGHT - player.size.y
        }
    }

    /**
     * Checks if the game should spawn new blocks. You can use the given timeSinceLastBlock
     * to determine wheter to spawn a new block or not.
     *
     * @param timeSinceLastBlock The time since the last block was spawned in milliseconds.
     * @return true if the game should spawn new blocks, false if not
     */
    private fun shouldSpawnNewBlocks(timeSinceLastBlock: Long): Boolean {
        return timeSinceLastBlock > 100
    }

    /**
     * Here we will implement logic for spawning new blocks that the player
     * has to dodge. The blocks should start at top of the screen at a random
     * X-coordinate. The the blocks should be added to the list `blocksToDodge`.
     */
    private fun spawnNewBlocks() {
        val position = Vector2(
            (Math.random() * (EngineConfig.VIEWPORT_WIDTH - 20)).toFloat(),
            EngineConfig.VIEWPORT_HEIGHT + 20f
        )
        val block = Rectangle(
            position = position,
            size = 20 x 20
        )
        blocksToDodge = blocksToDodge + listOf(block)
    }

    /**
     * Moves all the blocks in `blocksToDodge` list.
     *
     * @param delta The time since last update. This is used to make the movement-speed
     * not dependant of the update-rate.
     */
    private fun handleMoveBlocks(delta: Float) {
        blocksToDodge = blocksToDodge
            .map { it.move(0f x -blocksFallSpeed * delta) }
    }

    /**
     * Removes all the blocks that are out of bounds. Since the blocks start at the top
     * and fall downwards we can remove any blocks that are below the screen.
     */
    private fun removeBlocksOutOfBounds() {
        blocksToDodge = blocksToDodge
            .filter { it.position.y > 0 }
    }

    private fun playerIsColliding(): Boolean {
        return blocksToDodge.any { it.isCollidingWith(player) }
    }

    /**
     * This function should draw the player in the current position on the screen.
     * Use the utility method `drawRectangle` to draw on the screen.
     */
    private fun drawPlayer() {
        drawRectangle(player, Color.RED)
    }

    /**
     * This function should draw all the blocks the player has to dodge on the screen.
     * Use the utility method `drawRectangle` to draw on the screen.
     */
    private fun drawAllBlocksToDodge() {
        blocksToDodge.forEach { drawRectangle(it, Color.GRAY) }
    }

    /**
     * This is called when the player has lost the game. The initial implementation just
     * resets the game to the original state. Maybe you can come up with something more fun?
     * You could have a Score screen that displays how long you lasted etc.
     */
    private fun onGameLost() {
        player = Rectangle(
            position = EngineConfig.VIEWPORT_WIDTH / 2 x 200f,
            size = 20 x 20
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
            spawnNewBlocks()
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

    private fun drawRectangle(rectangle: Rectangle, color: Color) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            it.color = color
            it.rect(rectangle.position, rectangle.size)
        }
    }
}