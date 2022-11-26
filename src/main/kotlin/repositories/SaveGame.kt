package repositories

import com.google.gson.Gson
import services.GameService
import java.io.File
import java.io.FileReader

fun saveRound(game: GameService): GameService {
    val gson = Gson()
    File("game.json").writeText(gson.toJson(game))
    return game
}

fun readSavedGame(): GameService {
    val gson = Gson()
    return gson.fromJson(FileReader("game.json"), GameService::class.java)
}
