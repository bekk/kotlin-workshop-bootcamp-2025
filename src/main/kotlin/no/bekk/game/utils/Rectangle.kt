package no.bekk.game.utils

import com.badlogic.gdx.math.Vector2

data class Rectangle(
    var position: Vector2,
    var size: Vector2
) {
    /**
     * Moved the rectangle with the given vector. This method does not mutate
     * the rectangle, but rather returns a new Rectangle at the new position.
     */
    fun move(movement: Vector2): Rectangle =
        copy(position = position.plus(movement))

    /**
     * There are 2 cases to check here:
     *      1. Atleast one of the cornors of the other rectanle in within this rectangle
     *      2. The other rectangle is completly surrounding this rectangle
     */
    fun isCollidingWith(other: Rectangle): Boolean {
        val otherBottomLeft = other.position
        val otherTopLeft = other.position.plus(0f x other.size.y)
        val otherBottomRight = other.position.plus(other.size.x x 0f)
        val otherTopRight = other.position.plus(other.size.x x other.size.y)
        val otherCornors = listOf(otherBottomLeft, otherBottomRight, otherTopLeft, otherTopRight)

        return other.pointWitin(this.position) || otherCornors.any { pointWitin(it) }
    }

    fun pointWitin(point: Vector2): Boolean {
        val left = this.position.x
        val right = this.position.x + this.size.x
        val bottom = this.position.y
        val top = this.position.y + this.size.y

        return point.x < right && point.x > left && point.y > bottom && point.y < top
    }
}