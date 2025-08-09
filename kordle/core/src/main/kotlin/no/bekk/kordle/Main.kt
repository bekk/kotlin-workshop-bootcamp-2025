package no.bekk.kordle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import ktx.scene2d.*
import no.bekk.kordle.requests.*
import no.bekk.kordle.shared.dto.CreateUserRequest
import no.bekk.kordle.shared.dto.GjettResponse
import no.bekk.kordle.shared.dto.User
import no.bekk.kordle.shared.dto.UserOppgaveResult
import no.bekk.kordle.widgets.ErrorWidget
import no.bekk.kordle.widgets.GameOver
import no.bekk.kordle.widgets.GuessRow
import no.bekk.kordle.widgets.OnScreenKeyboard

class Main : KtxGame<KtxScreen>() {
    override fun create() {
        KtxAsync.initiate()

        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }
}

interface KordleUI {
    fun processReset()
    fun processAddLetter(letter: Char)
    fun processRemoveLetter()
    fun processGjett(gjett: GjettResponse)
    fun processInvalidGjett(error: String)
    fun processSetActiveRow(rowIndex: Int)
    fun processGameOver(won: Boolean)
}

class FirstScreen : KtxScreen, KordleUI {
    private val batch = SpriteBatch()
    private val stage = Stage(ScreenViewport()).also { Gdx.input.inputProcessor = it }
    private val kotlinLogo = Texture("kotlinLogo.png".toInternalFile())

    private val controller = KordleController(this)
    private var guessRows: MutableList<GuessRow> = mutableListOf()

    private val currentGuessRow: GuessRow
        get() = guessRows[controller.currentGuessIndex]

    private var isPaused = false
    private val guessTable: KTableWidget
    private val keyboard: OnScreenKeyboard
    private val gameOver: GameOver
    private val errorWidget: ErrorWidget

    private var user: User? = null

    private fun buildGuessRows() {
        guessTable?.clear()
        guessRows = (0 until controller.maxGuesses).map {
            GuessRow(guessTable, controller.wordLength ?: 6)
        }.toMutableList()
    }

    init {
        Scene2DSkin.defaultSkin = createSkin(this)

        val rootTable = scene2d.table {
            setFillParent(true)
        }
        rootTable.table {
            image(kotlinLogo) {
                it.width(28f).height(28f)
            }
            label("ORDLE", "large") {
                color = BekkColors.Natt
                it.spaceBottom(20f)
            }
        }
        rootTable.row()
        guessTable = rootTable.table {
            it
                .expandX()
                .expandY()
                .spaceBottom(20f)
        }
        buildGuessRows()
        rootTable.row()
        val keyboardTable = rootTable.table {
            it.fillX().expandX()
        }
        keyboard = OnScreenKeyboard(keyboardTable, controller)

        stage.addActor(rootTable)
        errorWidget = ErrorWidget(stage, controller)
        // if you change username, stats effectively reset
        fetchOrCreateUser("localKordleUser") {
            user = it
        }
        getTilfeldigOppgave(
            onSuccess = { controller.currentOppgave = it; errorWidget.hide() },
            onFailure = {
                errorWidget.show()
            }
        )
        gameOver = GameOver(stage, controller, errorWidget)


        stage.addListener(createKeyboardListener(object : KeyboardReceiver {
            override fun onLetterPressed(letter: Char) {
                if (!isPaused) controller.addLetter(letter)
            }

            override fun onBackspacePressed() {
                if (!isPaused)
                    controller.removeLetter()
            }

            override fun onEnterPressed() {
                if (!isPaused)
                    controller.submit()
            }

            override fun onEscapePressed() {
                if (user != null) {
                    getUserStats(user!!.id) { stats -> gameOver.setStats(stats) }
                }

                isPaused = gameOver.toggle()
            }
        }))
        currentGuessRow.setIsActive()
    }


    override fun processReset() {
        buildGuessRows()
        keyboard.reset()
        currentGuessRow.setIsActive()
    }

    override fun processAddLetter(letter: Char) {
        currentGuessRow.addLetter(letter)
    }

    override fun processRemoveLetter() {
        currentGuessRow.removeLetter()
    }

    override fun processGjett(gjett: GjettResponse) {
        currentGuessRow.markGuess(gjett)
        gjett.alleBokstavtreff.forEach { result ->
            keyboard.updateBestGuess(result.bokstavGjettet.lowercaseChar(), LetterGuessStatus.fromResponse(result))
        }
    }

    override fun processInvalidGjett(error: String) {
        currentGuessRow.shake()
    }

    override fun processSetActiveRow(rowIndex: Int) {
        currentGuessRow.setIsActive()
    }

    override fun processGameOver(won: Boolean) {
        if (user != null) {
            registerResult(
                UserOppgaveResult(
                    user!!.id,
                    controller.currentOppgave!!.oppgaveId,
                    won,
                    controller.currentGuessIndex + 1
                )
            ) { stats ->
                gameOver.setStats(stats)
            }
        }
        gameOver.show(won)
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(BekkColors.Dag)
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        batch.disposeSafely()
    }
}

fun fetchOrCreateUser(username: String, callback: (User) -> Unit) {
    getUser(username) { user ->
        if (user != null) callback(user)
        else createUser(CreateUserRequest(username), callback)
    }
}

