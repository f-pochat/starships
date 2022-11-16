package main

import WINDOW_HEIGHT
import WINDOW_WIDTH
import edu.austral.ingsis.starships.ui.*
import factories.createClassicInitialGameState
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage
import services.GameService

fun main() {
    launch(Starships::class.java)
}

class Starships : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    private val facade = ElementsViewFacade(imageResolver)
    private val keyTracker = KeyTracker()
    private var adapter = GameService(createClassicInitialGameState())

    override fun start(primaryStage: Stage) {
        adapter = adapter.addElements(facade.elements)

        facade.timeListenable.addEventListener(object : EventListener<TimePassed> {
            override fun handle(event: TimePassed) {
                adapter = adapter.onKeyFrame(event.currentTimeInSeconds - event.secondsSinceLastTime).adaptElements(facade.elements)
            }
        })

        keyTracker.keyPressedListenable.addEventListener(object : EventListener<KeyPressed> {
            override fun handle(event: KeyPressed) {
                adapter = adapter.keyPressed(event).addElements(facade.elements)
            }
        })
//        facade.collisionsListenable.addEventListener(CollisionListener())
//     keyTracker.keyPressedListenable.addEventListener(KeyPressedListener(starship))
        val root = facade.view
        root.id = "pane"
        val scene = Scene(root)
        keyTracker.scene = scene
        scene.stylesheets.add(this::class.java.classLoader.getResource("styles.css")?.toString())
        primaryStage.scene = scene
        primaryStage.height = WINDOW_HEIGHT
        primaryStage.width = WINDOW_WIDTH

        facade.start()
        facade.showCollider.set(false)
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
