package services.collider

import models.Bullet
import services.GameService

class BulletCollider(private val id: String) : Collider {
    override fun collide(gameService: GameService, collider: Collider): GameService =
        gameService.copy(
            bullets = gameService.bullets.filter { it.bulletId != id },
            keyMapHandler = gameService.keyMapHandler,
            score = if (collider is AsteroidCollider) {
                gameService.score + 10
            } else {
                gameService.score
            }
        )

    override fun getCollidable(gameService: GameService): Bullet {
        return gameService.bullets.find { it.bulletId == id }!!
    }
}
