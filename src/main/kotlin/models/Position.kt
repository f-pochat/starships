package models

import kotlin.math.cos
import kotlin.math.sin

data class Position(
    val x: Double,
    val y: Double
) {
    fun move(movementVector: MovementVector): Position = copy(
        x = x + movementVector.speed * cos(movementVector.rotation),
        y = y + movementVector.speed * sin(movementVector.rotation)
    )
}
