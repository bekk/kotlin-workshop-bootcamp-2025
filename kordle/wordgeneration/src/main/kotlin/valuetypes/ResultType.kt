package valuetypes

enum class ResultType(val requestCode: Char, val resultCode: String) {
    Exact('e', "exact"),
    Inflect('i', "inflect"),
    Freetext('f', "freetext"),
    Similar('s', "similar");

    companion object {
        fun fromResultCode(code: String): ResultType? {
            return entries.find { it.resultCode == code }
        }
    }
}
