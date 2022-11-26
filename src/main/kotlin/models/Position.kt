package models

import kotlin.math.cos
import kotlin.math.sin

data class Position(
    val x: Double,
    val y: Double
) {
    fun move(movementVector: MovementVector, deltaTime: Double): Position = copy(
        x = x + (movementVector.speed * -sin(Math.toRadians(movementVector.rotation)) * deltaTime),
        y = y + (movementVector.speed * cos(Math.toRadians(movementVector.rotation)) * deltaTime)
    )
}
