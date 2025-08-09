package valuetypes

enum class Malform(val code: String) {
    Bokmal("bm"),
    Nynorsk("nn"),
    Both("bm,nn");

    override fun toString() = code

    companion object {
        /* Fra docs:
        > parameter dform=int forkorter dict til tallkoder slik:
        > ['bm']:1 ['nn']:2 ['bm','nn']:3
         */
        fun fromInt(value: Int): Malform {
            return when (value) {
                1 -> Bokmal
                2 -> Nynorsk
                3 -> Both
                else -> throw IllegalArgumentException("Invalid Målform int value: $value")
            }
        }

        fun fromStringList(value: List<String>): Malform {
            val hasBokmal = value.contains(Bokmal.code)
            val hasNynorsk = value.contains(Nynorsk.code)
            return when {
                hasBokmal && hasNynorsk -> Both
                hasBokmal -> Bokmal
                hasNynorsk -> Nynorsk
                else -> throw IllegalArgumentException("Invalid Målform string list: $value")
            }
        }
    }
}
