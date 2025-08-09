package no.bekk.introduction

fun double(number: Int): Int {
    return number * 2
}

fun maybeDouble(number: Int?): Int? {
    return if (number != null) {
        double(number)
    } else {
        null
    }
}

fun main() {
    val number = 2
    val doubledNumber = double(number)
    println(doubledNumber)

    val maybeNumber: Int? = null
    val maybeDoubledNumber = maybeDouble(maybeNumber)
    println(maybeDoubledNumber)
}
