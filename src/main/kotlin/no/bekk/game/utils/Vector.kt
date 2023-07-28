package no.bekk.game.utils

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

operator fun Vector2.component1() = this.x
operator fun Vector2.component2() = this.y
operator fun Vector2.minus(v: Vector2): Vector2 = Vector2(this.x - v.x, this.y - v.y)
operator fun Vector2.plus(v: Vector2): Vector2 = Vector2(this.x + v.x, this.y + v.y)
operator fun Vector2.unaryMinus(): Vector2 = Vector2(-x, -y)
operator fun Vector2.times(direction: Vector2): Vector2 {
    val (x, y) = this
    val (dx, dy) = direction
    return Vector2(x * dx, y * dy)
}

operator fun Vector2.times(scl: Float): Vector2 {
    val (x, y) = this
    return Vector2(x * scl, y * scl)
}

fun Vector2.perpendicular(): Vector2 = Vector2(-this.y, x)

fun Vector2.copy() = Vector2(this.x, this.y)
fun Vector3.v2(): Vector2 = Vector2(x, y)

infix fun Float.x(y: Float) = Vector2(this, y)
infix fun Int.x(y: Int) = Vector2(this.toFloat(), y.toFloat())
fun Vector2.offset(x: Float = 0f, y: Float = 0f): Vector2 = this.x + x x this.y + y

fun pointerWithinSquare(pos: Vector2, size: Vector2): Boolean {
    val (px, py) = scaledPointer()
    val (x, y) = pos
    val (sx, sy) = size
    return x <= px && x + sx >= px && y <= py && y + sy >= py
}

fun lerp(t: Float, min: Float, max: Float): Float = min * (1 - t) + max * t
