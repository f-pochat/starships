package models

data class Bullet(
    val position: Position,
    val damage: Int,
    val movementVector: MovementVector
) : Collidable, Movable {
    override fun collide(collider: Collidable): Bullet {
        return this // Check how to do to delete the bullet
    }

    override fun move(): Movable = copy(position = position.move(movementVector))
}
