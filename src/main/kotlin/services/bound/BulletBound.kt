package services.bound

import services.GameService

class BulletBound(private val id: String) : Bound {
    override fun reachBound(gameService: GameService): GameService = gameService
        .copy(bullets = gameService.bullets.filter { it.bulletId != id })
}
