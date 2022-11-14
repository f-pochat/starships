package models

data class Bullet(
    val bulletId: String,
    val position: Position,
    val damage: Int,
    val movementVector: MovementVector
) : Collidable, Movable {
    override fun collide(collider: Collidable): Bullet {
        return this // Check how to do to delete the bullet
    }

    override fun move(): Movable = copy(position = position.move(movementVector))
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
