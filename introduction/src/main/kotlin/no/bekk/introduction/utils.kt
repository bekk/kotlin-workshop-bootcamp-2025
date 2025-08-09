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
    4,
)

val aurora = BootcampCoach(
    "Aurora",
    Avdeling.DESIGN,
    7,
)

val bendik = BootcampCoach(
    "Bendik",
    Avdeling.BMC,
    4,
)

val frikk = BootcampCoach(
    "Frikk",
    Avdeling.TEKNOLOGI,
    4,
)

val gabriel = BootcampCoach(
    "Gabriel",
    Avdeling.TEKNOLOGI,
    4,
)

val hanna = BootcampCoach(
    "Hanna",
    Avdeling.TEKNOLOGI,
    4,
)

val hansKristian = BootcampCoach(
    "Hans Kristian",
    Avdeling.TEKNOLOGI,
    9,
)

val ingrid = BootcampCoach(
    "Ingrid",
    Avdeling.TEKNOLOGI,
    5,
)

val kristin = BootcampCoach(
    "Kristin",
    Avdeling.DESIGN,
    3,
)

val morten = BootcampCoach(
    "Morten",
    Avdeling.BMC,
    6,
)

val oleReidar = BootcampCoach(
    "Ole Reidar",
    Avdeling.TEKNOLOGI,
    7,
)

val sara = BootcampCoach(
    "Sara",
    Avdeling.TEKNOLOGI,
    3,
)

val sivert = BootcampCoach(
    "Sivert",
    Avdeling.TEKNOLOGI,
    4,
)

val vilde = BootcampCoach(
    "Vilde",
    Avdeling.DESIGN,
    2,
)

val coacher2025 = listOf<BootcampCoach>(august, aurora, bendik, frikk, gabriel, hanna, hansKristian, ingrid, kristin, morten, oleReidar, sara, sivert, vilde)