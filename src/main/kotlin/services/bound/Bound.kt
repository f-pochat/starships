package services.bound

import services.GameService

interface Bound {
    fun reachBound(gameService: GameService): GameService
}
