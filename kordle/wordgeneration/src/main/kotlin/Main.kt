import valuetypes.Malform
import valuetypes.ResultType
import java.io.File

fun main() {
    val henter = OrdHenterDeluxe3000()
    (4..6).forEach { n ->
        val gjettOrd = henter.getGjettOrdForLength(n)
        val file = File("server/src/main/resources/gjettord-$n.txt")
        file.createNewFile()
        file.writeText(gjettOrd.joinToString("\n"))
    }
}

class OrdHenterDeluxe3000 {
    private val client = SuggestClient()
    fun getFasitOrdForLength(
        length: Int,
        /* antall ord å hente - merk at dette gjøres alfabetisk.
         Hvis man vil ha et begrenset antall, kan det være bedre å hente ut så mange som overhodet mulig,
         og så gjøre .shuffled().take(n) */
        count: Int = 1_000_000
    ): Set<String> {
        val results = client.suggest(
            // finner ord med lengde n
            "_".repeat(length),
            count = count,
            malform = Malform.Bokmal,
        )
        return results[ResultType.Exact]
            ?.map { word -> word.lemma }
            ?.filter(::isValidOrd)
            ?.toSet()
            ?: emptySet()
    }

    fun getGjettOrdForLength(
        length: Int,     /* antall ord å hente - merk at dette gjøres alfabetisk.
     Hvis man vil ha et begrenset antall, kan det være bedre å hente ut så mange som overhodet mulig,
     og så gjøre .shuffle().take(n) */
        count: Int = 1_000_000
    ): Set<String> {
        val results = client.suggest(
            // finner ord med lengde n
            "_".repeat(length),
            count = count,
            malform = Malform.Bokmal
        )
        return results.values
            .flatMap { it.map { entry -> entry.lemma } }
            .filter(::isValidOrd)
            .toSet()
    }
}

val validLetters = ('a'..'z') + ('A'..'Z') + "æøåÆØÅ".toList()
fun isValidOrd(word: String): Boolean {
    return word.all { it in validLetters }
}
