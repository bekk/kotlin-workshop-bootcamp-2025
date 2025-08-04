package no.bekk.introduction

fun skrivUtStringlengde(streng: String?): Int {
    return streng!!.length
}

fun main() {
    println("Lengden på strengen er: ${skrivUtStringlengde(null)}")

    val streng1: String = "Hei, dette er en streng"
    println("Lengden på streng1 er: ${skrivUtStringlengde(streng1)}")
}