package no.bekk.introduction

fun main() {
    println("Årets coacher er $coacher2025!")

    // TODO: Skriv kode som summerer opp antall år alle de har vært i bekk
    val antallAarIBekk = coacher2025.map { it.aarIBekk }.reduce { aarIBekk1, aarIBekk2 -> aarIBekk1 + aarIBekk2 }

    // TODO: Skriv kode som filtrerer ut coacher som ikke er i teknologi-avdelingen
    val teknologiCoacher = coacher2025.filter { it.avdeling == Avdeling.TEKNOLOGI }

    // TODO: Skriv kode som kopierer lista, men hvor Johan er i BMC og Ragnhild er i Design.
    val endredeCoacher = coacher2025.map { coach ->
        when (coach.name) {
            "Johan" -> coach.copy(avdeling = Avdeling.BMC)
            "Ragnhild" -> coach.copy(avdeling = Avdeling.DESIGN)
            else -> coach
        }
    }

    println("Antall år i bekk er $antallAarIBekk ")

    println("TeknologiCoachene er $teknologiCoacher")

    println("Lista over endrede coacher er $endredeCoacher")
}
