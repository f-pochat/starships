package models

data class Starship(
    val starshipId: String,
    val position: Position,
    val color: String,
    val lives: Double,
    val movementVector: MovementVector,
    val damage: Double,
    val bullets: Int
) : Movable, Collidable {

    fun accelerate(): Starship = copy(movementVector = movementVector.accelerate())
    fun stop(): Starship = copy(movementVector = movementVector.stop())
    fun turnLeft(): Starship = copy(movementVector = movementVector.turnLeft())
    fun turnRight(): Starship = copy(movementVector = movementVector.turnRight())

    override fun collide(collider: Collidable): Starship {
        return when (collider) {
            is Asteroid -> copy(movementVector = movementVector.fullStop(), lives = lives - 1)
            is Bullet -> this
            is Starship -> this
            else -> this
        }
    }

    override fun move(deltaTime: Double): Starship = copy(position = position.move(movementVector, deltaTime))
    override fun getId(): String {
        return starshipId
    }

    override fun getX(): Double {
        return position.x
    }

    override fun getY(): Double {
        return position.y
    }

    override fun getRotation(): Double {
        return movementVector.rotation
    }
}
