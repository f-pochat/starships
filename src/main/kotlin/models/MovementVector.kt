package models

data class MovementVector(
    val speed: Double,
    val rotation: Double
) {
    fun accelerate(): MovementVector = copy(speed = speed * 1.5)
    fun stop(): MovementVector = copy(speed = speed * 0.5)
    fun turnLeft(): MovementVector = copy(rotation = rotation + 5)
    fun turnRight(): MovementVector = copy(rotation = rotation - 5)
    fun fullStop(): MovementVector = copy(speed = 0.0)
}
