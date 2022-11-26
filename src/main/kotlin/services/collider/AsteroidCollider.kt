package services.collider

import models.Asteroid
import services.GameService

class AsteroidCollider(val id: String) : Collider {
    override fun collide(gameService: GameService, collider: Collider): GameService =
        gameService.copy(
            asteroids = (
                gameService.asteroids.filter { it.asteroidId != id } + listOf(
                    getCollidable(gameService).collide(
                        collider.getCollidable(gameService)
                    )
                )
                ),
            keyMapHandler = gameService.keyMapHandler
        )

    override fun getCollidable(gameService: GameService): Asteroid {
        return gameService.asteroids.find { it.asteroidId == id }!!
    }
}
