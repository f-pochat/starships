package models

import javafx.scene.input.KeyCode

data class Settings(val config: Config, val players: List<Player>)

data class Config(
    val keyMap: Map<String, KeyEvent>,
    val asteroidConfig: AsteroidConfig
)

data class AsteroidConfig(
    val spawnProbs: Double,
    val size: Double
)

data class Player(
    val id: String,
    val color: String,
    val lives: Double,
    val keyMap: KeyMap,
    val gun: Gun
)

data class Gun(
    val damage: Double,
    val bullets: Int
)

data class KeyMap(
    val accelerate: KeyEvent,
    val stop: KeyEvent,
    val turnLeft: KeyEvent,
    val turnRight: KeyEvent,
    val shoot: KeyEvent
) {
    fun findAction(keyCode: KeyCode, keyAction: KeyAction): String? {
        return when (keyCode) {
            accelerate.keyCode -> if (accelerate.action === keyAction) {
                "accelerate"
            } else {
                null
            }

            stop.keyCode -> if (stop.action === keyAction) {
                "stop"
            } else {
                null
            }
            turnLeft.keyCode -> if (turnLeft.action === keyAction) {
                "turnLeft"
            } else {
                null
            }
            turnRight.keyCode -> if (turnRight.action === keyAction) {
                "turnRight"
            } else {
                null
            }
            shoot.keyCode -> if (shoot.action === keyAction) {
                "shoot"
            } else {
                null
            }
            else -> null
        }
    }
}

data class KeyEvent(
    val keyCode: KeyCode,
    val action: KeyAction
)

enum class KeyAction {
    MAINTAIN,
    CLICK
}
