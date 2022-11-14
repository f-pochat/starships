import models.*

fun createClassicGame(configSettings: ConfigSettings): Game {
    return Game(createClassicInitialGameState(), configSettings)
}

fun createClassicInitialGameState(): GameState {
    return GameState(KeyState.PLAY, listOf(createStarship(Position(200.0, 200.0), 10, createGun(1, 1))))
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
    return Asteroid(String.hashCode().toString(), size, position, movementVector)
}

fun createGun(bulletsPerShot: Int, damage: Int): Gun {
    return Gun(listOf(), bulletsPerShot, damage)
}
