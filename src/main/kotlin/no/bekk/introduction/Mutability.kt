package no.bekk.introduction

// Feltene i `Person` kan ikke endres fordi de er deklarert med `val`
data class Person(val name: String, val age: Int)

fun main() {
    // Oppgave 1
    val patrick = Person("Patrick", 28)
    //patrick.name = "Espen"

    // Oppgave 2:
    val viktigeTall = mutableListOf(1, 2, 3)
    skrivUtTallListeOgTalletFire(viktigeTall)

    // Ikke fjern eller kommenter ut denne linja, den er viktig for oppgave 2 :)
    funkSjonalitetSomIkkeLikerTalletFire(viktigeTall)
}

fun skrivUtTallListeOgTalletFire(viktigeTall: MutableList<Int>) {
    viktigeTall.add(4)
    println(viktigeTall)
}

fun funkSjonalitetSomIkkeLikerTalletFire(viktigeTall: MutableList<Int>) {
    if (4 in viktigeTall) {
        throw IllegalArgumentException("Tallet 4 er ikke tillatt i denne listen")
    }
}
