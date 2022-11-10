package models

data class Gun(
    val bullets: List<Bullet>,
    val bulletsPerShot: Int,
    val damage: Int
) {
    fun shoot(initialPosition: Position, movementVector: MovementVector): Gun =
        copy(bullets = bullets + listOf(Bullet(initialPosition, damage, movementVector)))
}
