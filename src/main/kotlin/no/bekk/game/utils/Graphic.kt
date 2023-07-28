package no.bekk.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

fun getGraphic(path: String, txSize: TxSize): TextureRegion {
    val texture = Texture(Gdx.files.internal(path))
    return TextureRegion(texture, txSize.x, txSize.y, txSize.sizeX, txSize.sizeY)
}

fun getSprite(path: String, size: Pair<Int, Int>, position: Vector2): Sprite =
    Sprite(
        textureRegion = getGraphic(path, TxSize.Grid(0, 0, size)),
        position = position
    )

data class Sprite(
    val textureRegion: TextureRegion,
    val position: Vector2
)

sealed class TxSize(val x: Int, val y: Int, val sizeX: Int, val sizeY: Int) {
    data class Grid(val row: Int, val col: Int, val size: Pair<Int, Int>) : TxSize(col * size.first, row * size.second, size.first, size.second) {
        override fun toString(): String = "g,$row,$col,${size.first},${size.second}"
    }

    companion object {
        val s8 = Pair(8, 8)
        val s16 = Pair(16, 16)
        val s32 = Pair(32, 32)
        val s64 = Pair(64, 64)
        val s128 = Pair(128, 128)
    }
}
