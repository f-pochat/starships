package models

data class MovementVector(
    val speed: Double,
    val rotation: Double
) {
    fun accelerate(): MovementVector {
        return if (speed < 100) {
            copy(speed = speed + 15)
        } else {
            this
        }
    }

    fun stop(): MovementVector {
        return if (speed > -100) {
            copy(speed = speed - 15)
        } else {
            this
        }
    }

    fun turnLeft(): MovementVector = copy(rotation = rotation - 10)
    fun turnRight(): MovementVector = copy(rotation = rotation + 10)
    fun fullStop(): MovementVector = copy(speed = 0.0)
}
