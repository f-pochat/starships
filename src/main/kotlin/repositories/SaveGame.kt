package repositories

import com.google.gson.Gson
import models.Game
import java.io.File

private fun saveRound(game: Game): Game {
    val gson = Gson()
    File("../../resources/game.txt").writeText(gson.toJson(game))
    return game
}
