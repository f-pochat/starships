package models

data class Asteroid(
    val id: String,
    val size: Int,
    val position: Position,
    val movementVector: MovementVector
) : Collidable, Movable {
    override fun move(): Asteroid = copy(position = position.move(movementVector))

    override fun collide(collider: Collidable): Asteroid {
        return when (collider) {
            is Bullet -> copy(size = size - collider.damage)
            is Asteroid -> this
            is Starship -> this
        }
    }
}
