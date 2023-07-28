package no.bekk.introduction

// Feltene i `Person` kan ikke endres fordi de er deklarert med `val`
data class Person(val name: String, val age: Int)

fun main() {
    // Oppgave 1
    val sondre = Person("Sondre", 26)
    // sondre.name = "Gaute"

    // Oppgave 2:
    val numbers = listOf(1, 2, 3)
    // numbers.add(4)
}
