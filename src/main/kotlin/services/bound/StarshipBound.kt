package services.bound

import services.GameService

class StarshipBound(private val id: String) : Bound {
    override fun reachBound(gameService: GameService): GameService =
        gameService.copy(starships = gameService.starships.filter { it.starshipId != id })
}
