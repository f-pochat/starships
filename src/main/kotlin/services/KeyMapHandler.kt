package services

import javafx.scene.input.KeyCode
import models.KeyAction
import models.KeyEvent
import models.KeyMap
import models.KeyState
import repositories.saveRound
import services.transformer.*
import kotlin.system.exitProcess

data class KeyMapHandler(
    val playerKeyMap: List<Pair<String, KeyMap>>,
    val gameKeyMap: Map<String, KeyEvent>
) {
    fun generateKeyPressed(keyCode: KeyCode, gameService: GameService): GameService {
        for (i in playerKeyMap) {
            if (!i.second.findAction(keyCode, KeyAction.CLICK).isNullOrBlank() && gameService.state == KeyState.PLAY) {
                return chooseAction(i.second.findAction(keyCode, KeyAction.CLICK), gameService, i.first)
            }
        }
        return gameKeys(keyCode, gameService)
    }

    private fun chooseAction(action: String?, gameService: GameService, id: String): GameService {
        return when (action) {
            "accelerate" -> StarshipAccelerator(id).transform(gameService)
            "stop" -> StarshipStopper(id).transform(gameService)
            "turnLeft" -> StarshipLeftTurner(id).transform(gameService)
            "turnRight" -> StarshipRightTurner(id).transform(gameService)
            "shoot" -> StarshipShooter(id).transform(gameService)
            else -> gameService
        }
    }

    private fun gameKeys(keyCode: KeyCode, gameService: GameService): GameService =
        when (gameKeyMap.entries.find { it.value.keyCode == keyCode && it.value.action == KeyAction.CLICK }?.key) {
            "pause" -> if (gameService.state == KeyState.PLAY) {
                gameService.copy(state = KeyState.PAUSE)
            } else {
                gameService.copy(state = KeyState.PLAY)
            }

            "save" -> {
                println("Save")
                try {
                    saveRound(gameService)
                } finally {
                    exitProcess(0)
                }
            }

            else -> gameService
        }

    fun performKeysDown(keys: Set<KeyCode>, gameService: GameService): GameService {
        var game = gameService
        for (i in playerKeyMap) {
            for (j in keys) {
                if (!i.second.findAction(j, KeyAction.MAINTAIN).isNullOrBlank() && gameService.state == KeyState.PLAY) {
                    game = chooseAction(i.second.findAction(j, KeyAction.MAINTAIN), game, i.first)
                }
            }
        }
        return game
    }
}
