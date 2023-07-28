package no.bekk.introduction

class Konsulent(
    val name: String,
)

fun main() {
    val gaute = Konsulent("Gaute")
    println(gaute)

    val gaute2 = Konsulent("Gaute")
    println("gaute1 == gaute2: ${gaute == gaute2}")
}
