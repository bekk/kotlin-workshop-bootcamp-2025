package no.bekk.introduction

// Oppgave 1 - Skriv om denne til en extension function
fun getInfo(coach: BootcampCoach): String {
    return "${coach.name} er i avdeling ${coach.avdeling} og har jobbet ${coach.yearsInBekk} år i Bekk"
}

// Oppgave 2 - Skriv om denne til en extension function
fun hasWorkedLongerThan(coach1: BootcampCoach, coach2: BootcampCoach): Boolean {
    return coach1.yearsInBekk > coach2.yearsInBekk
}

// Oppgave 3 - Skriv om disse til extension functions, og bruk chaining
fun addYears(coach: BootcampCoach, years: Int): BootcampCoach {
    return BootcampCoach(coach.name, coach.avdeling, coach.yearsInBekk + years)
}

fun withAvdeling(coach: BootcampCoach, avdeling: Avdeling): BootcampCoach {
    return BootcampCoach(coach.name, avdeling, coach.yearsInBekk)
}

fun main() {
    val sondre = BootcampCoach("Sondre", Avdeling.TEKNOLOGI, 2)
    val ingrid = BootcampCoach("Ingrid", Avdeling.TEKNOLOGI, 3)

    // Kommenter denne inn når du har gjort oppgave 1
    // print(sondre.getInfo())

    // Kommenter denne inn når du har gjort oppgave 2
    // print(ingrid.hasWorkedLongerThan(sondre))

    // Oppgave 3 - kan du gjøre dette mer leselig ved hjelp av extension functions?
    print(getInfo(addYears(withAvdeling(ingrid, Avdeling.BMC), 1)))

    // Ferdig? Bra jobba! Se løsningsforslag i README.md og gå videre til neste 🚀
}
