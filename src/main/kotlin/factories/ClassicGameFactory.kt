package factories

import models.*

fun createClassicInitialGameState(): GameState {
    return GameState(
        KeyState.PLAY,
        listOf(
            createStarship(Position(400.0, 400.0), 10, createGun(1, 1)),
            createAsteroid(10, Position(200.0, 200.0), MovementVector(0.0, 100.0))
        )
    )
}

fun createStarship(position: Position, lives: Int, gun: Gun): Starship {
    return Starship(
        "spaceship",
        position,
        lives,
        MovementVector(0.0, 180.0),
        gun
    )
}

fun createAsteroid(size: Int, position: Position, movementVector: MovementVector): Asteroid {
    return Asteroid("asteroid", size, position, movementVector)
}

fun createGun(bulletsPerShot: Int, damage: Int): Gun {
    return Gun(listOf(), bulletsPerShot, damage)
}

fun createBullet(position: Position, damage: Int, rotataion: Double): Bullet {
    return Bullet("bullet-" + (0..1000).random(), position, damage, MovementVector(200.0, rotataion))
}
