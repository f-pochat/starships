package services

import factories.createAsteroid
import javafx.scene.input.KeyCode
import models.*
import services.bound.AsteroidBound
import services.bound.Bound
import services.bound.BulletBound
import services.bound.StarshipBound
import services.collider.AsteroidCollider
import services.collider.BulletCollider
import services.collider.Collider
import services.collider.StarshipCollider

data class GameService(
    val state: KeyState,
    val starships: List<Starship>,
    val keyMapHandler: KeyMapHandler,
    val asteroidConfig: AsteroidConfig,
    val bullets: List<Bullet> = listOf(),
    val asteroids: List<Asteroid> = listOf(),
    val score: Int
) {

    fun keyFrameOnElements(deltaTime: Double): GameService {
        val filteredGameService = filterDeadElements()
        return if (state == KeyState.PLAY) {
            filteredGameService.copy(
                asteroids = spawnAsteroid(deltaTime, filteredGameService),
                starships = filteredGameService.starships.map { it.move(deltaTime) },
                bullets = filteredGameService.bullets.map { it.move(deltaTime) },
                asteroidConfig = asteroidConfig.copy(spawnProbs = asteroidConfig.spawnProbs - 0.01)
            )
        } else {
            filteredGameService
        }
    }

    private fun filterDeadElements(): GameService =
        copy(
            asteroids = asteroids.filter { it.size > 3 },
            starships = starships.filter { it.lives > 0 }
        )

    private fun spawnAsteroid(deltaTime: Double, gameService: GameService): List<Asteroid> {
        return if ((0..asteroidConfig.spawnProbs.toInt()).random() == 1) {
            gameService.asteroids.map { it.move(deltaTime) } + listOf(newAsteroid())
        } else {
            gameService.asteroids.map { it.move(deltaTime) }
        }
    }

    private fun newAsteroid(): Asteroid = createAsteroid(asteroidConfig.size)

    fun getMovables(): List<Movable> = starships + bullets + asteroids

    fun transformService(key: KeyCode): GameService = keyMapHandler.generateKeyPressed(key, this)

    fun collide(id1: String, id2: String): GameService {
        return collideObjects(id1, id2).collideObjects(id2, id1)
    }

    fun setKeyState(keyState: KeyState) = copy(state = keyState)
    fun reachBounds(id: String): GameService =
        getBound(getMovables().find { it.getId() == id }!!, id).reachBound(this)

    fun transformManyActions(keys: Set<KeyCode>): GameService {
        return keyMapHandler.performKeysDown(keys, this)
    }

    private fun collideObjects(id1: String, id2: String): GameService =
        getCollider(getMovables().find { it.getId() === id1 }!!, id1).collide(
            this,
            getCollider(getMovables().find { it.getId() === id2 }!!, id2)
        )

    private fun getCollider(movable: Movable, id: String): Collider {
        return when (movable) {
            is Starship -> StarshipCollider(id)
            is Bullet -> BulletCollider(id)
            is Asteroid -> AsteroidCollider(id)
        }
    }

    private fun getBound(movable: Movable, id: String): Bound {
        return when (movable) {
            is Starship -> StarshipBound(id)
            is Bullet -> BulletBound(id)
            is Asteroid -> AsteroidBound(id)
        }
    }
}
