package models

sealed interface Movable {
    fun move(): Movable
}
