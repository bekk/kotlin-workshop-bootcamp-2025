package no.bekk.introduction

data class BekkAnsatt(val name: String){

    fun skrivUtIntroduksjonMedHobby() {
        println("Hei! Mitt navn er $name. Jeg er glad i å kode kotlin")
    }
}
fun main() {
    val ingrid = BekkAnsatt("Ingrid")
    ingrid.skrivUtIntroduksjonMedHobby()
}
