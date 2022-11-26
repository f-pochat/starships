package main

import WINDOW_HEIGHT
import WINDOW_WIDTH
import edu.austral.ingsis.starships.ui.*
import factories.createClassicInitialGameState
import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import models.KeyState
import repositories.readConfig
import repositories.readSavedGame
import services.GameAdapter
import kotlin.system.exitProcess

fun main() {
    launch(Starships::class.java)
}

class Starships : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    private val facade = ElementsViewFacade(imageResolver)
    private val keyTracker = KeyTracker()
    private var game = GameAdapter(createClassicInitialGameState(readConfig())).addElements(facade.elements)
    private val keys = mutableSetOf<KeyCode>()

    override fun start(primaryStage: Stage) {
        val (score, pane, root) = mainGameScene()
        val menu = menuScene(primaryStage, pane)
        val (scoreExit: StackPane, txtScore: Label) = exitScene(primaryStage, root, score)

        timeListener(txtScore, primaryStage, scoreExit)

        keyPressedListener()

        keyReleased()

        collisionListener()

        reachBounds()

        keyTracker.scene = menu
        menu.stylesheets.add(this::class.java.classLoader.getResource("styles.css")?.toString())
        menu.stylesheets.add("https://fonts.googleapis.com/css2?family=VT323&display=swap")
        primaryStage.height = WINDOW_HEIGHT
        primaryStage.width = WINDOW_WIDTH
        primaryStage.scene = menu
        facade.start()
        facade.showCollider.set(false)
        facade.showGrid.set(false)
        keyTracker.start()
        primaryStage.show()
    }

    private fun reachBounds() {
        facade.reachBoundsListenable.addEventListener(object : EventListener<ReachBounds> {
            override fun handle(event: ReachBounds) {
                game = game
                    .reachBounds(event)
                    .removeElements(facade.elements)
            }
        })

        facade.outOfBoundsListenable.addEventListener(object : EventListener<OutOfBounds> {
            override fun handle(event: OutOfBounds) {
                if (game.getGameState() == KeyState.PLAY) {
                    game = game
                        .outOfBounds(event)
                        .removeElements(facade.elements)
                }
            }
        })
    }

    private fun collisionListener() {
        facade.collisionsListenable.addEventListener(object : EventListener<Collision> {
            override fun handle(event: Collision) {
                game = game
                    .collide(event)
                    .adaptCollidingObjects(facade.elements)
            }
        })
    }

    private fun keyReleased() {
        keyTracker.keyReleasedListenable.addEventListener(object : EventListener<KeyReleased> {
            override fun handle(event: KeyReleased) {
                keys.remove(event.key)
            }
        })
    }

    private fun keyPressedListener() {
        keyTracker.keyPressedListenable.addEventListener(object : EventListener<KeyPressed> {
            override fun handle(event: KeyPressed) {
                keys.add(event.key)
                game = game.clickKeyPressed(event)
            }
        })
    }

    private fun timeListener(
        txtScore: Label,
        primaryStage: Stage,
        scoreExit: StackPane
    ) {
        facade.timeListenable.addEventListener(object : EventListener<TimePassed> {
            override fun handle(event: TimePassed) {
                game = game.onKeyFrame(event.secondsSinceLastTime)
                    .removeElements(facade.elements)
                    .keyPressed(keys)
                    .addElements(facade.elements)
                    .adaptMovingElements(facade.elements)
                if (game.notContainsStarship()) {
                    txtScore.text = game.getScore()
                    primaryStage.scene.root = scoreExit
                }
                txtScore.text = game.getScore()
            }
        })
    }

    private fun mainGameScene(): Triple<StackPane, StackPane, Parent> {
        // Score
        val score = StackPane()
        val txt = Label("0")
        txt.style = "-fx-font-family: VT323; -fx-font-size: 50"
        txt.textFill = Color.color(0.9, 0.9, 0.9)
        val div = HBox()
        div.alignment = Pos.TOP_RIGHT
        div.children.addAll(txt)
        div.padding = Insets(10.0, 10.0, 10.0, 10.0)

        val rect = Rectangle(WINDOW_WIDTH - 10, WINDOW_HEIGHT - 50)
        rect.fill = Color.TRANSPARENT
        rect.stroke = Color.RED
        score.children.addAll(div, rect)

        val pane = StackPane()
        val root = facade.view
        pane.children.addAll(root, score)
        root.id = "pane"
        return Triple(score, pane, root)
    }

    private fun exitScene(
        primaryStage: Stage,
        root: Parent,
        score: StackPane
    ): Pair<StackPane, Label> {
        val scoreExit = StackPane()
        val gameOver = Label("Game Over")
        val txtScore = Label("0")
        val exit = Label("Exit")
        val playAgain = Label("Play again")

        gameOver.style = "-fx-font-family: VT323; -fx-font-size: 120"
        gameOver.textFill = Color.color(0.9, 0.9, 0.9)

        txtScore.style = "-fx-font-family: VT323; -fx-font-size: 100"
        txtScore.textFill = Color.color(0.9, 0.9, 0.9)

        playAgain.style = "-fx-font-family: VT323; -fx-font-size: 70"
        playAgain.textFill = Color.color(0.9, 0.9, 0.9)

        playAgain.setOnMouseClicked {
            val newPane = StackPane()
            primaryStage.scene.root = newPane
            newPane.children.addAll(root, score)
            game = GameAdapter(createClassicInitialGameState(readConfig())).addElements(facade.elements)
                .setGameState(KeyState.PLAY)
        }
        playAgain.setOnMouseEntered {
            playAgain.textFill = Color.CYAN
            playAgain.cursor = Cursor.HAND
        }

        playAgain.setOnMouseExited {
            playAgain.textFill = Color.color(0.7, 0.7, 0.7)
        }

        exit.textFill = Color.color(0.7, 0.7, 0.7)
        exit.style = "-fx-font-family: VT323; -fx-font-size: 50"

        exit.setOnMouseClicked {
            exitProcess(0)
        }
        exit.setOnMouseEntered {
            exit.textFill = Color.CYAN
            exit.cursor = Cursor.HAND
        }

        exit.setOnMouseExited {
            exit.textFill = Color.color(0.7, 0.7, 0.7)
        }

        val divExit = VBox(50.0)
        divExit.alignment = Pos.CENTER
        divExit.children.addAll(gameOver, txtScore, playAgain, exit)
        divExit.id = "pane"

        scoreExit.children.addAll(divExit)
        return Pair(scoreExit, txtScore)
    }

    private fun menuScene(primaryStage: Stage, pane: StackPane): Scene {
        // Menu
        val title = Label("Starships")
        title.textFill = Color.color(0.9, 0.9, 0.9)
        title.style = "-fx-font-family: VT323; -fx-font-size: 100;"

        val logo = ImageView("logo.png")
        logo.isPreserveRatio = true
        logo.fitHeight = 200.0

        val newGame = Label("New Game")
        newGame.textFill = Color.color(0.7, 0.7, 0.7)
        newGame.style = "-fx-font-family: VT323; -fx-font-size: 50"
        newGame.setOnMouseClicked {
            primaryStage.scene.root = pane
            game = game.setGameState(KeyState.PLAY)
        }

        newGame.setOnMouseEntered {
            newGame.textFill = Color.CYAN
            newGame.cursor = Cursor.HAND
        }

        newGame.setOnMouseExited {
            newGame.textFill = Color.color(0.7, 0.7, 0.7)
        }

        val loadGame = Label("Load Game")
        loadGame.textFill = Color.color(0.7, 0.7, 0.7)
        loadGame.style = "-fx-font-family: VT323; -fx-font-size: 50;"
        loadGame.setOnMouseClicked {
            primaryStage.scene.root = pane
            game = GameAdapter(readSavedGame()).setGameState(KeyState.PLAY)
        }
        loadGame.setOnMouseEntered {
            loadGame.textFill = Color.CYAN
            loadGame.cursor = Cursor.HAND
        }

        loadGame.setOnMouseExited {
            loadGame.textFill = Color.color(0.7, 0.7, 0.7)
        }

        val options = HBox(70.0)
        options.alignment = Pos.CENTER
        options.children.addAll(newGame, loadGame)

        val layout = VBox(50.0)
        layout.id = "menu"
        layout.alignment = Pos.CENTER
        layout.children.addAll(title, logo, options)

        val menu = Scene(layout)
        return menu
    }

    override fun stop() {
        facade.stop()
        keyTracker.stop()
    }
}
