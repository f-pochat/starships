package models

data class Gun(
    val bullets: List<Bullet>,
    val bulletsPerShot: Int,
    val damage: Int
)