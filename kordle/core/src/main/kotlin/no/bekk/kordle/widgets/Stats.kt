package no.bekk.kordle.widgets

import com.badlogic.gdx.utils.Align
import ktx.scene2d.KTableWidget
import ktx.scene2d.label
import no.bekk.kordle.shared.dto.StatsForUser

class Stats(private val parent: KTableWidget, private val maxGuesses: Int) {
    init {
        build()
    }

    var stats: StatsForUser? = null
        set(value) {
            field = value
            build()
        }

    fun build() {
        parent.clear()
        if (stats == null) {
            parent.label("Ingen statistikk tilgjengelig", "small")
            return
        }
        parent.label("Statistikk for bruker #${stats?.userId}", "small") {
            it.spaceBottom(10f).colspan(2)
        }
        parent.row()
        parent.label("Antall gjett", "small") {
            it.align(Align.left).padRight(32f)
        }
        parent.label("Antall oppgaver", "small") {
            it.align(Align.right)
        }
        parent.row()
        (1..maxGuesses).forEach { attemptCount ->
            val count = stats?.amountOfOppgaverSolvedByGjett?.get(attemptCount) ?: 0
            parent.label(attemptCount.toString(), "small") {
                it.align(Align.left)
            }
            parent.label("$count oppgaver", "small") {
                it.align(Align.right)
            }
            parent.row()
        }
        parent.label("Feilet", "small") {
            it.align(Align.left).padRight(32f)
        }
        parent.label("${stats?.amountOfOppgaverFailed} oppgaver", "small") {
            it.align(Align.right)
        }
        parent.row()
    }
}
