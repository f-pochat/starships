package models

data class Starship(
    private val id: String,
    private val position: Position,
    private val lives: Int,
    private val movementVector: MovementVector,
    private val gun: Gun
) : Movable, Collidable {

    fun accelerate(): Starship = copy(movementVector = movementVector.accelerate())
    fun stop(): Starship = copy(movementVector = movementVector.stop())
    fun turnLeft(): Starship = copy(movementVector = movementVector.turnLeft())
    fun turnRight(): Starship = copy(movementVector = movementVector.turnRight())
    fun shoot(): Starship = copy(gun = gun.shoot(position, movementVector))

    override fun collide(collider: Collidable): Starship {
        return when (collider) {
            is Asteroid -> copy(movementVector = movementVector.fullStop())
            is Bullet -> copy(lives = lives - collider.damage)
            is Starship -> this
        }
    }

    override fun move(): Starship = copy(position = position.move(movementVector))
}
