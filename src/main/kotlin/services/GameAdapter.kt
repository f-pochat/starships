package services

import edu.austral.ingsis.starships.ui.*
import javafx.scene.input.KeyCode
import models.*

data class GameAdapter(private val service: GameService) {

    fun onKeyFrame(deltaTime: Double): GameAdapter =
        copy(service = service.keyFrameOnElements(deltaTime))

    fun adaptCollidingObjects(elements: MutableMap<String, ElementModel>): GameAdapter {
        service.asteroids.forEach {
            elements.getValue(it.getId()).width.set(it.size * 10)
            elements.getValue(it.getId()).height.set(it.size * 10)
        }
        return this
    }

    fun adaptMovingElements(elements: Map<String, ElementModel>): GameAdapter {
        service.getMovables().forEach {
            elements.getValue(it.getId()).x.set(it.getX())
            elements.getValue(it.getId()).y.set(it.getY())
            elements.getValue(it.getId()).rotationInDegrees.set(it.getRotation())
        }
        return this
    }

    fun keyPressed(keys: Set<KeyCode>): GameAdapter =
        copy(service = service.transformManyActions(keys))

    fun clickKeyPressed(event: KeyPressed) =
        copy(service = service.transformService(event.key))

    fun addElements(elements: MutableMap<String, ElementModel>): GameAdapter {
        val newElements = service.getMovables().filter { !elements.keys.contains(it.getId()) }
        newElements.forEach { elements[it.getId()] = elementToUI(it) }
        return this
    }

    fun removeElements(elements: MutableMap<String, ElementModel>): GameAdapter {
        val oldElements = elements.keys.filterNot { service.getMovables().map { a -> a.getId() }.contains(it) }
        oldElements.forEach { elements.remove(it) }
        return this
    }

    fun reachBounds(event: ReachBounds): GameAdapter = copy(service = service.reachBounds(event.id))

    fun outOfBounds(event: OutOfBounds): GameAdapter = copy(service = service.reachBounds(event.id))

    fun collide(collision: Collision): GameAdapter =
        copy(service = service.collide(collision.element1Id, collision.element2Id))

    fun getScore(): String = service.score.toString()

    fun notContainsStarship() = service.starships.isEmpty()

    fun getGameState(): KeyState = service.state

    fun setGameState(keyState: KeyState): GameAdapter = copy(service = service.setKeyState(keyState))

    companion object {
        fun elementToUI(movable: Movable): ElementModel {
            return when (movable) {
                is Starship -> starshipToStarshipUI(movable)
                is Asteroid -> asteroidToAsteroidUI(movable)
                is Bullet -> bulletToBulletUI(movable)
            }
        }

        private fun starshipToStarshipUI(starship: Starship): ElementModel {
            return ElementModel(
                starship.getId(),
                starship.position.x,
                starship.position.y,
                60.0,
                70.0,
                starship.movementVector.rotation,
                ElementColliderType.Triangular,
                ImageRef("spaceship_" + starship.color, 60.0, 70.0)
            )
        }

        private fun asteroidToAsteroidUI(asteroid: Asteroid): ElementModel {
            return ElementModel(
                asteroid.asteroidId,
                asteroid.position.x,
                asteroid.position.y,
                asteroid.size * 10,
                asteroid.size * 10,
                asteroid.movementVector.rotation,
                ElementColliderType.Elliptical,
                ImageRef("asteroid", asteroid.size * 10, asteroid.size * 10)
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
    }
}
