package adapters

import edu.austral.ingsis.starships.ui.*
import javafx.scene.input.KeyCode
import models.GameState
import models.Starship

class GameAdapter(private val state: GameState) {

    fun starshipToStarshipUI(): ElementModel {
        val starship = state.movables[0] as Starship
        return ElementModel(
            starship.getId(),
            starship.position.x,
            starship.position.y,
            70.0,
            70.0,
            starship.movementVector.rotation,
            ElementColliderType.Triangular,
            ImageRef("spaceship", 70.0, 70.0)
        )
    }
//
//    fun moveStarship(sta): ElementModel {
//        return starshipToStarshipUI(s)
//    }

    fun accelerate(): GameAdapter =
        GameAdapter(state.copy(movables = listOf((state.movables[0] as Starship).accelerate())))

    fun stop(): GameAdapter = GameAdapter(state.copy(movables = listOf((state.movables[0] as Starship).stop())))
    fun turnLeft(): GameAdapter = GameAdapter(state.copy(movables = listOf((state.movables[0] as Starship).turnLeft())))
    fun turnRight(): GameAdapter = GameAdapter(state.copy(movables = listOf((state.movables[0] as Starship).turnRight())))

    fun onKeyFrame(): GameAdapter {
        return GameAdapter(state.copy(movables = state.movables.map { it.move() }))
    }

    fun adaptElements(elements: Map<String, ElementModel>): GameAdapter {
        state.movables.forEach {
            elements.getValue(it.getId()).x.set(it.getX())
            elements.getValue(it.getId()).y.set(it.getY())
            elements.getValue(it.getId()).rotationInDegrees.set(it.getRotation())
        }
        return this
    }

    fun keyPressed(event: KeyPressed): GameAdapter {
        return when (event.key) {
            KeyCode.UP -> accelerate()
            KeyCode.DOWN -> stop()
            KeyCode.LEFT -> turnLeft()
            KeyCode.RIGHT -> turnRight()
            else -> this
        }
    }
}
