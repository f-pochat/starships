package models

import kotlin.math.cos
import kotlin.math.sin

data class Position(
    val x: Double,
    val y: Double
) {
    fun move(movementVector: MovementVector): Position = copy(
        x = x + movementVector.speed * -sin(Math.toRadians(movementVector.rotation)),
        y = y + movementVector.speed * cos(Math.toRadians(movementVector.rotation))
    )
}
