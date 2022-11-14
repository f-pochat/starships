package models

data class Starship(
    val starshipId: String,
    val position: Position,
    val lives: Int,
    val movementVector: MovementVector,
    val gun: Gun
) : Movable, Collidable {

    fun accelerate(): Starship = copy(movementVector = movementVector.accelerate())
    fun stop(): Starship = copy(movementVector = movementVector.stop())
    fun turnLeft(): Starship = copy(movementVector = movementVector.turnLeft())
    fun turnRight(): Starship = copy(movementVector = movementVector.turnRight())
//    fun shoot(): Starship = copy(gun = gun.shoot(position, movementVector))

    override fun collide(collider: Collidable): Starship {
        return when (collider) {
            is Asteroid -> copy(movementVector = movementVector.fullStop())
            is Bullet -> copy(lives = lives - collider.damage)
            is Starship -> this
            else -> this
        }
    }

    override fun move(): Starship = copy(position = position.move(movementVector))
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
