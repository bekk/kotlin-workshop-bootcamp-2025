package no.bekk.kordle.server.service

import org.springframework.stereotype.Service
import java.io.File

@Service
class OrdValidatorService {
    private val wordsByLength = mutableMapOf<Int, Set<String>>()

    fun isValid(word: String): Boolean {
        val length = word.length
        val words = wordsByLength.computeIfAbsent(length) {
            loadWordsForLength(length) ?: throw IllegalArgumentException("No words available for length $length.")
        }
        return words.contains(word.uppercase())
    }

    private fun loadWordsForLength(length: Int): Set<String>? {
        val file = File("server/src/main/resources/gjettord-$length.txt")
        if (!file.exists()) return null
        return file.readLines().map { it.trim().uppercase() }.toSet()
    }
}
