package no.bekk.introduction

data class BootcampCoach(
    val name: String,
    val avdeling: Avdeling,
    val aarIBekk: Int,
)
enum class Avdeling {
    TEKNOLOGI, DESIGN, BMC
}

val ingrid = BootcampCoach(
    "Ingrid",
    Avdeling.TEKNOLOGI,
    3,
)

val kristian = BootcampCoach(
    "Kristian",
    Avdeling.TEKNOLOGI,
    4,
)

val ragnhild = BootcampCoach(
    "Ragnhild",
    Avdeling.TEKNOLOGI,
    4,
)

val johan = BootcampCoach(
    "Johan",
    Avdeling.TEKNOLOGI,
    8,
)

val ida = BootcampCoach(
    "Ida",
    Avdeling.TEKNOLOGI,
    6,
)

val gabriel = BootcampCoach(
    "Gabriel",
    Avdeling.TEKNOLOGI,
    2,
)

val aurora = BootcampCoach(
    "Aurora",
    Avdeling.DESIGN,
    5,
)

val preben = BootcampCoach(
    "Preben",
    Avdeling.DESIGN,
    2,
)

val morten = BootcampCoach(
    "Morten",
    Avdeling.BMC,
    4,
)

val coacher2023 = listOf<BootcampCoach>(ingrid, kristian, johan, ida, gabriel, ragnhild, aurora, preben, morten, ida)