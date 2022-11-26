package models

data class MovementVector(
    val speed: Double,
    val rotation: Double
) {
    fun accelerate(): MovementVector {
        return if (speed < 300) {
            copy(speed = speed + 5)
        } else {
            this
        }
    }

    fun stop(): MovementVector {
        return if (speed > -300) {
            copy(speed = speed - 5)
        } else {
            this
        }
    }

    fun turnLeft(): MovementVector = copy(rotation = rotation - 1)
    fun turnRight(): MovementVector = copy(rotation = rotation + 1)
    fun fullStop(): MovementVector = copy(speed = 0.0)
}
