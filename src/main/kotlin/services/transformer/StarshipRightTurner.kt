package services.transformer

import services.GameService
import services.picker.StarshipPicker

class StarshipRightTurner(private val id: String) : Transformer {
    private val picker = StarshipPicker()
    override fun transform(gameService: GameService): GameService {
        val pick = picker.pick(gameService.starships, id)
        return gameService.copy(starships = pick.first + listOf(pick.second.turnRight()), keyMapHandler = gameService.keyMapHandler)
    }
}
