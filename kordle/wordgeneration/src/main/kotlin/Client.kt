import kotlinx.serialization.json.Json
import org.springframework.web.client.RestClient
import valuetypes.*

/**
 * En klient for å hente ord fra https://ord.uib.no, APIet bak https://ordbokene.no/
 * Basert på dokumentasjonen
 * https://ord.uib.no/ord_2_API.html
 * https://v1.ordbokene.no/api/swagger-ui.html#/
 */
class SuggestClient {
    private val json = Json { ignoreUnknownKeys = true }
    private val restClient = RestClient.builder().baseUrl("https://ord.uib.no/api").build()

    fun suggest(
        /**
         * Søkeord, støtter følgende wildcards:
         * '%' eller '*' matcher 0 eller flere tegn
         * '_' matcher ett tegn
         * "_*" kan kombineres for å matche 1 eller flere tegn
         *
         * Se dokumentasjon for mer detaljer
         */
        query: String,
        wordClasses: Set<WordClass>? = null,
        count: Int = 10,
        malform: Malform = Malform.Both,
        include: Set<ResultType> = setOf(
            ResultType.Exact,
            ResultType.Freetext,
            ResultType.Inflect,
            ResultType.Similar
        ),
        dformInt: Boolean = true
    ): Map<ResultType, List<SuggestEntry>> {
        val responseBody = restClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/suggest")
                    .queryParam("q", query)
                    .queryParam("n", count)
                    .queryParam("dict", malform.code)
                    .queryParam("include", include.joinToString("") { it.requestCode.toString() })
                    .apply {
                        if (wordClasses != null) queryParam("wc", wordClasses.joinToString(",") { it.code })
                        if (dformInt) queryParam("dform", "int")
                    }
                    .build()
            }
            .retrieve()
            .body(String::class.java)

        val parsed = json.decodeFromString<RawSuggestResponse>(responseBody!!)
        return parsed.parseEntries()
    }
}
