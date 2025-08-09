package valuetypes

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class RawSuggestResponse(
    val q: String,
    val cnt: Int,
    val cmatch: Int,
    val a: Map<String, List<List<JsonElement>>>
) {
    fun parseEntries(): Map<ResultType, List<SuggestEntry>> {
        return a.mapNotNull { (key, tuples) ->
            val resultType = ResultType.Companion.fromResultCode(key) ?: return@mapNotNull null
            val resultsForType = tuples.mapNotNull(::parseEntry)
            resultType to resultsForType
        }.toMap()
    }

    private fun parseEntry(
        tuple: List<JsonElement>
    ): SuggestEntry? {
        val lemma = tuple.getOrNull(0)?.jsonPrimitive?.contentOrNull ?: return null
        val dictsJson = tuple.getOrNull(1) ?: return null

        val malform = when {
            dictsJson is JsonPrimitive && dictsJson.intOrNull != null -> Malform.Companion.fromInt(dictsJson.int)

            dictsJson is JsonArray -> Malform.Companion.fromStringList(
                dictsJson.mapNotNull { it.jsonPrimitive.contentOrNull }
            )

            else -> null
        } ?: return null

        return SuggestEntry(lemma, malform)
    }
}
