package models

data class Bullet(
    val bulletId: String,
    val position: Position,
    val damage: Double,
    val movementVector: MovementVector
) : Movable, Collidable {
    override fun collide(collider: Collidable): Bullet {
        return this
    }

    override fun move(deltaTime: Double): Bullet = copy(position = position.move(movementVector, deltaTime))
    override fun getId(): String {
        return bulletId
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
