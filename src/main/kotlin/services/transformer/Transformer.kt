package services.transformer

import services.GameService

interface Transformer {
    fun transform(gameService: GameService): GameService
}
