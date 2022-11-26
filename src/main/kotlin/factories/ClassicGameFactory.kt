package factories

import WINDOW_HEIGHT
import WINDOW_WIDTH
import models.*
import services.GameService
import services.KeyMapHandler
import kotlin.math.cos
import kotlin.math.sin

fun createClassicInitialGameState(settings: Settings): GameService {
    return GameService(
        KeyState.PAUSE,
        settings.players.map { createStarship(it.id, it.color, it.lives, it.gun.damage, it.gun.bullets) },
        KeyMapHandler(settings.players.map { it.id to it.keyMap }, settings.config.keyMap),
        settings.config.asteroidConfig,
        score = 0
    )
}

fun createStarship(id: String, color: String, lives: Double, damage: Double, bullets: Int): Starship {
    return Starship(
        id,
        Position(
            (0..WINDOW_WIDTH.toInt() - 100).random().toDouble(),
            (0..WINDOW_HEIGHT.toInt() - 100).random().toDouble()
        ),
        color,
        lives,
        MovementVector(0.0, 180.0),
        damage,
        bullets
    )
}

fun createAsteroid(size: Double): Asteroid {
    return Asteroid(
        "asteroid-" + (0..1000).random(),
        size,
        Position(
            (0..WINDOW_WIDTH.toInt() - 100).random().toDouble(),
            (0..WINDOW_HEIGHT.toInt() - 100).random().toDouble()
        ),
        MovementVector(100.0, (0 until 360).random().toDouble())
    )
}

fun createBullet(position: Position, damage: Double, rotation: Double): Bullet {
    return Bullet(
        "bullet-" + (0..1000).random(),
        bulletPos(position, rotation),
        damage,
        MovementVector(1000.0, rotation)
    )
}

private fun bulletPos(starshipPos: Position, starshipRot: Double): Position =
    Position(
        x = starshipPos.x + (50 * -sin(Math.toRadians(starshipRot))),
        y = starshipPos.y + (50 * cos(Math.toRadians(starshipRot)))
    )
