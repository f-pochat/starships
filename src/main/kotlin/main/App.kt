package main

import adapters.GameAdapter
import createClassicInitialGameState
import edu.austral.ingsis.starships.ui.*
import edu.austral.ingsis.starships.ui.ElementColliderType.*
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage

fun main() {
    launch(Starships::class.java)
}

class Starships() : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    private val facade = ElementsViewFacade(imageResolver)
    private val keyTracker = KeyTracker()
    private var adapter = GameAdapter(createClassicInitialGameState())

    override fun start(primaryStage: Stage) {
        facade.elements["spaceship"] = adapter.starshipToStarshipUI()

        facade.timeListenable.addEventListener(object : EventListener<TimePassed> {
            override fun handle(event: TimePassed) {
                adapter = adapter.onKeyFrame().adaptElements(facade.elements)
            }
        })

        keyTracker.keyPressedListenable.addEventListener(object : EventListener<KeyPressed> {
            override fun handle(event: KeyPressed) {
                adapter = adapter.keyPressed(event)
            }
        })
//        facade.collisionsListenable.addEventListener(CollisionListener())
//     keyTracker.keyPressedListenable.addEventListener(KeyPressedListener(starship))

        val scene = Scene(facade.view)
        keyTracker.scene = scene

        primaryStage.scene = scene
        primaryStage.height = 800.0
        primaryStage.width = 800.0

        facade.start()
        keyTracker.start()
        primaryStage.show()
    }

    override fun stop() {
        facade.stop()
        keyTracker.stop()
    }
}

class CollisionListener() : EventListener<Collision> {
    override fun handle(event: Collision) {
        println("${event.element1Id} ${event.element2Id}")
    }
}

class KeyPressedListener(private val starship: ElementModel) : EventListener<KeyPressed> {
    override fun handle(event: KeyPressed) {
        when (event.key) {
            KeyCode.UP -> starship.y.set(starship.y.value - 5)
            KeyCode.DOWN -> starship.y.set(starship.y.value + 5)
            KeyCode.LEFT -> starship.x.set(starship.x.value - 5)
            KeyCode.RIGHT -> starship.x.set(starship.x.value + 5)
            else -> {}
        }
    }
}
