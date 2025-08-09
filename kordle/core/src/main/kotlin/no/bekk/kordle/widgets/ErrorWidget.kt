package no.bekk.kordle.widgets

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import ktx.actors.onClick
import ktx.graphics.copy
import ktx.scene2d.*
import no.bekk.kordle.BekkColors
import no.bekk.kordle.KordleController
import no.bekk.kordle.requests.getTilfeldigOppgave

class ErrorWidget(private val parent: Stage, private val controller: KordleController) {
    private val label: Label
    private val errorTable: KTableWidget = scene2d.table {
        background = (skin.getDrawable("white") as TextureRegionDrawable).tint(BekkColors.Natt.copy(alpha = 0.9f))
        row()
        label = label("Klarte ikke å hente ut en tilfeldig oppgave fra backenden", "small") {
            color = BekkColors.ErrorRod
        }
        row()
        button {
            label("Prøv på ny", "large")
            color = BekkColors.Vann1
            onClick {
                getTilfeldigOppgave(
                    onSuccess = { oppgaveResponse ->
                        controller.currentOppgave = oppgaveResponse
                        controller.reset()
                        hide()
                    },
                    onFailure = {
                        show()
                    }
                )
            }
        }
        setFillParent(true)
    }

    init {
        errorTable.isVisible = false
        parent.addActor(errorTable)
    }

    fun show() {
        errorTable.isVisible = true
        errorTable.toFront()
    }

    fun hide() {
        errorTable.isVisible = false
    }
}
