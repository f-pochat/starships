package repositories

import com.google.gson.Gson
import models.GameState
import java.io.File

private fun saveRound(game: GameState): GameState {
    val gson = Gson()
    File("../../resources/game.txt").writeText(gson.toJson(game))
    return game
}
