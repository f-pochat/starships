package services.bound

import services.GameService

class AsteroidBound(private val id: String) : Bound {
    override fun reachBound(gameService: GameService): GameService = gameService
        .copy(asteroids = gameService.asteroids.filter { it.asteroidId != id })
}
