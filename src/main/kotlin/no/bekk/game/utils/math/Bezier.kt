package no.bekk.game.utils.math

import com.badlogic.gdx.math.Vector2
import no.bekk.game.utils.component1
import no.bekk.game.utils.component2
import kotlin.math.max
import kotlin.math.min

class Bezier(
    private val p0: Vector2,
    private val p1: Vector2,
    private val p2: Vector2,
    private val p3: Vector2,
) {

    private fun getPt(p1: Float, p2: Float, t: Float): Float {
        val diff = p2 - p1
        return p1 + (diff * t)
    }

    fun point(p: Float): Vector2 {
        val t = min(1f, max(p, 0f))
        val (x1, y1) = p0
        val (x2, y2) = p1
        val (x3, y3) = p2
        val (x4, y4) = p3

        val xa = getPt(x1, x2, t)
        val ya = getPt(y1, y2, t)
        val xb = getPt(x2, x3, t)
        val yb = getPt(y2, y3, t)
        val xc = getPt(x3, x4, t)
        val yc = getPt(y3, y4, t)

        val xm = getPt(xa, xb, t)
        val ym = getPt(ya, yb, t)
        val xn = getPt(xb, xc, t)
        val yn = getPt(yb, yc, t)

        val x = getPt(xm, xn, t);
        val y = getPt(ym, yn, t);

        return Vector2(x, y)
    }

    fun debugline(sampleSize: Int = 50): List<Vector2> =
        (0..sampleSize).map {
            point(it * (1 / sampleSize.toFloat()))
        }

    fun debugPoints() = listOf(p0, p1, p2, p3)
}