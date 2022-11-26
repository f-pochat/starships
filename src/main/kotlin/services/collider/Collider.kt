package services.collider

import models.Collidable
import services.GameService

sealed interface Collider {
    fun collide(gameService: GameService, collider: Collider): GameService
    fun getCollidable(gameService: GameService): Collidable
}
