package services.collider

import models.Starship
import services.GameService

class StarshipCollider(private val id: String) : Collider {

    override fun collide(gameService: GameService, collider: Collider): GameService =
        gameService.copy(
            starships = (
                gameService.starships.filter { it.starshipId != id } + listOf(
                    getCollidable(gameService).collide(
                        collider.getCollidable(gameService)
                    )
                )
                ),
            keyMapHandler = gameService.keyMapHandler
        )

    override fun getCollidable(gameService: GameService): Starship {
        return gameService.starships.find { it.starshipId == id }!!
    }
}
