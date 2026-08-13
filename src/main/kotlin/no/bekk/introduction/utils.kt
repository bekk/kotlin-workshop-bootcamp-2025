package no.bekk.introduction

data class BootcampCoach(
    val name: String,
    val avdeling: Avdeling,
    val aarIBekk: Int,
)
enum class Avdeling {
    TEKNOLOGI, DESIGN, BMC
}

val august = BootcampCoach(
    "August",
    Avdeling.TEKNOLOGI,
    5,
)

val aurora = BootcampCoach(
    "Aurora",
    Avdeling.DESIGN,
    8,
)

val eirik = BootcampCoach(
    "Bendik",
    Avdeling.BMC,
    2,
)

val frikk = BootcampCoach(
    "Frikk",
    Avdeling.TEKNOLOGI,
    5,
)

val haavard = BootcampCoach(
    "Håvard",
    Avdeling.TEKNOLOGI,
    8,
)

val hanna = BootcampCoach(
    "Hanna",
    Avdeling.DESIGN,
    2,
)

val hansKristian = BootcampCoach(
    "Hans Kristian",
    Avdeling.TEKNOLOGI,
    10,
)

val ingrid = BootcampCoach(
    "Ingrid",
    Avdeling.TEKNOLOGI,
    6,
)

val johannes = BootcampCoach(
    "Johannes",
    Avdeling.TEKNOLOGI,
    3,
)

val karen = BootcampCoach(
    "Karen",
    Avdeling.TEKNOLOGI,
    3,
)

val kristin = BootcampCoach(
    "Kristin",
    Avdeling.DESIGN,
    4,
)

val oleReidar = BootcampCoach(
    "Ole Reidar",
    Avdeling.TEKNOLOGI,
    8,
)

val sara = BootcampCoach(
    "Sara",
    Avdeling.TEKNOLOGI,
    4,
)

val sivert = BootcampCoach(
    "Sivert",
    Avdeling.TEKNOLOGI,
    5,
)

val sofie = BootcampCoach(
    "Sofie",
    Avdeling.BMC,
    6,
)

val tuva = BootcampCoach(
    "Tuva",
    Avdeling.TEKNOLOGI,
    3,
)

val vilde = BootcampCoach(
    "Vilde",
    Avdeling.DESIGN,
    3,
)

val coacher2026 = listOf<BootcampCoach>(august, aurora, eirik, frikk, haavard, hanna, hansKristian, ingrid, karen, kristin, johannes, oleReidar, sara, sivert, sofie, tuva, vilde)