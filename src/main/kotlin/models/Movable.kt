package models

sealed interface Movable {
    fun move(deltaTime: Double): Movable
    fun getId(): String

    fun getX(): Double

    fun getY(): Double

    fun getRotation(): Double
}
