package valuetypes

// Ordklasser, fra https://universaldependencies.org/u/pos/index.html
enum class WordClass(val code: String) {
    Adjective("ADJ"),
    Adposition("ADP"),
    Adverb("ADV"),
    Auxiliary("AUX"),
    CoordinatingConjunction("CCONJ"),
    Determiner("DET"),
    Interjection("INTJ"),
    Noun("NOUN"),
    Numeral("NUM"),
    Particle("PART"),
    Pronoun("PRON"),
    ProperNoun("PROPN"),
    Punctuation("PUNCT"),
    SubordinatingConjunction("SCONJ"),
    Symbol("SYM"),
    Verb("VERB"),
    Other("X");
}
