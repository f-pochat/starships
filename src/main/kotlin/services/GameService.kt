package services

import edu.austral.ingsis.starships.ui.*
import factories.createBullet
import javafx.scene.input.KeyCode
import models.*

class GameService(val state: GameState) {

    private fun starshipToStarshipUI(starship: Starship): ElementModel {
        return ElementModel(
            starship.getId(),
            starship.position.x,
            starship.position.y,
            60.0,
            70.0,
            starship.movementVector.rotation,
            ElementColliderType.Triangular,
            ImageRef("spaceship", 60.0, 70.0)
        )
    }

    private fun asteroidToAsteroidUI(asteroid: Asteroid): ElementModel {
        return ElementModel(
            asteroid.asteroidId,
            asteroid.position.x,
            asteroid.position.y,
            50.0,
            50.0,
            asteroid.movementVector.rotation,
            ElementColliderType.Elliptical,
            ImageRef("asteroid", 50.0, 50.0)
        )
    }

    private fun bulletToBulletUI(bullet: Bullet): ElementModel {
        return ElementModel(
            bullet.bulletId,
            bullet.position.x,
            bullet.position.y,
            10.0,
            5.0,
            bullet.movementVector.rotation,
            ElementColliderType.Elliptical,
            ImageRef("bullet", 15.0, 5.0)
        )
    }

    private fun accelerate(): GameService =
        GameService(state.copy(movables = listOf((state.movables[0] as Starship).accelerate())))

    private fun stop(): GameService = GameService(state.copy(movables = listOf((state.movables[0] as Starship).stop())))
    private fun turnLeft(): GameService =
        GameService(state.copy(movables = listOf((state.movables[0] as Starship).turnLeft())))

    private fun turnRight(): GameService =
        GameService(state.copy(movables = listOf((state.movables[0] as Starship).turnRight())))

    private fun shoot(): GameService = GameService(
        state.copy(
            movables = state.movables + listOf(
                createBullet(
                    (state.movables[0] as Starship).position,
                    (state.movables[0] as Starship).gun.damage,
                    (state.movables[0] as Starship).movementVector.rotation
                )
            )
        )
    )

    fun onKeyFrame(
        deltaTime: Double
    ): GameService {
        return GameService(
            state.copy(
                movables = state.movables.map { it.move(deltaTime) }
                // .filter { (it.getX() < WINDOW_WIDTH) && (it.getX() > 0) && (it.getY() < WINDOW_HEIGHT) && (it.getY() > 0) }
            )
        )
    }

    fun adaptElements(elements: Map<String, ElementModel>): GameService {
        state.movables.forEach {
            elements.getValue(it.getId()).x.set(it.getX())
            elements.getValue(it.getId()).y.set(it.getY())
            elements.getValue(it.getId()).rotationInDegrees.set(it.getRotation())
        }
        return this
    }

    fun keyPressed(event: KeyPressed): GameService {
        return when (event.key) {
            KeyCode.UP -> accelerate()
            KeyCode.DOWN -> stop()
            KeyCode.LEFT -> turnLeft()
            KeyCode.RIGHT -> turnRight()
            KeyCode.SPACE -> shoot()
            else -> this
        }
    }

    fun addElements(elements: MutableMap<String, ElementModel>): GameService {
        val newElements = state.movables.filter { !elements.keys.contains(it.getId()) }
        val oldElements = elements.keys.filter { state.movables.map { a -> a.getId() }.contains(it) }
        newElements.forEach { elements[it.getId()] = elementToUI(it) }
        // oldElements.forEach { elements.remove(it) }
        return this
    }

    private fun elementToUI(movable: Movable): ElementModel {
        return when (movable) {
            is Starship -> starshipToStarshipUI(movable)
            is Asteroid -> asteroidToAsteroidUI(movable)
            is Bullet -> bulletToBulletUI(movable)
        }
    }
}
