package no.bekk.kordle.widgets

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import ktx.actors.onClick
import ktx.graphics.copy
import ktx.scene2d.*
import no.bekk.kordle.BekkColors
import no.bekk.kordle.KordleController
import no.bekk.kordle.requests.getFasitord
import no.bekk.kordle.requests.getTilfeldigOppgave
import no.bekk.kordle.shared.dto.StatsForUser

class GameOver(
    private val parent: Stage,
    private val controller: KordleController,
    private val errorWidget: ErrorWidget
) {
    private val label: Label
    private val stats: Stats
    private val fasitOrdLabel: Label
    private val table: KTableWidget = scene2d.table {
        background = (skin.getDrawable("white") as TextureRegionDrawable).tint(BekkColors.Natt.copy(alpha = 0.9f))
        label = label("Game over", "large") {
            color = BekkColors.Dag
        }
        row()
        label("Ordet var:", "small") {
            color = BekkColors.Dag
        }
        row()
        fasitOrdLabel = label("", "small") {
            color = BekkColors.Dag
        }
        row()
        button {
            label("Ny oppgave", "small")
            color = BekkColors.Vann1
            onClick {
                getTilfeldigOppgave(
                    onSuccess = { oppgaveResponse ->
                        controller.currentOppgave = oppgaveResponse
                        controller.reset()
                        hide()
                        errorWidget.hide()
                    },
                    onFailure = {
                        errorWidget.show()
                    }
                )
            }
        }
        row()
        table {
        }.also {
            stats = Stats(it, controller.maxGuesses)
        }
        setFillParent(true)
    }

    init {
        table.isVisible = false
        parent.addActor(table)
    }

    fun toggle(): Boolean {
        this.label.setText("Pauset")
        val isVisible = !table.isVisible
        table.isVisible = isVisible
        return isVisible
    }

    fun setStats(stats: StatsForUser?) {
        this.stats.stats = stats
    }

    fun show(won: Boolean) {
        setFasitOrdLabel(controller.currentOppgave!!.oppgaveId)
        this.label.setText(
            if (won) {
                "Du vant!"
            } else {
                "Du tapte!"
            }
        )
        table.isVisible = true
        table.toFront()
    }

    fun hide() {
        table.isVisible = false
    }

    fun setFasitOrdLabel(oppgaveId: Int) {
        getFasitord(
            oppgaveId = oppgaveId,
            onSuccess = { response ->
                this.fasitOrdLabel.setFontScale(1f)
                this.fasitOrdLabel.color = BekkColors.Ild1
                this.fasitOrdLabel.setText(response.fasitOrd)
            },
            onFailure = { error ->
                this.fasitOrdLabel.setFontScale(0.7f)
                this.fasitOrdLabel.color = BekkColors.ErrorRod
                this.fasitOrdLabel.setText("Fasitord kunne ikke hentes")
            }
        )
    }
}

