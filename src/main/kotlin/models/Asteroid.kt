package models

data class Asteroid(
    val asteroidId: String,
    val size: Int,
    val position: Position,
    val movementVector: MovementVector
) : Collidable, Movable {
    override fun move(deltaTime: Double): Asteroid = copy(position = position.move(movementVector, deltaTime))
    override fun getId(): String {
        return asteroidId
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

    override fun collide(collider: Collidable): Asteroid {
        return when (collider) {
            is Bullet -> copy(size = size - collider.damage)
            is Asteroid -> this
            is Starship -> this
            else -> this
        }
    }
}
