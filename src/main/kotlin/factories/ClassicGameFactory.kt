import models.*

fun createClassicGame(configSettings: ConfigSettings): Game {
    return Game(createClassicInitialGameState(), configSettings)
}

fun createClassicInitialGameState(): GameState {
    return GameState(KeyState.PLAY, listOf())
}

fun createStarship(position: Position, lives: Int, gun: Gun): Starship {
    return Starship(
        String.hashCode().toString(),
        position,
        lives,
        MovementVector(0.0, 0.0),
        gun
    )
}

fun createAsteroid(size: Int, position: Position, movementVector: MovementVector): Asteroid {
    return Asteroid(String.hashCode().toString(), size, position, movementVector)
}

fun createGun(bulletsPerShot: Int, damage: Int): Gun {
    return Gun(listOf(), bulletsPerShot, damage)
}
