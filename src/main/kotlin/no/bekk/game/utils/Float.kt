package no.bekk.game.utils

import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.round(precision: Int = 2) = (this * 10f.pow(precision)).roundToInt() / 10f.pow(precision.toFloat())
fun Float.percent(): Int = (this * 100).roundToInt()
