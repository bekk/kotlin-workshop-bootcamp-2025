package no.bekk.introduction

class Konsulent(
    val name: String,
)

fun main() {
    val patrick = Konsulent("Patrick")
    println(patrick)

    val patrick2 = Konsulent("Patrick")
    println("patrick == patrick2: ${patrick == patrick2}")
}
