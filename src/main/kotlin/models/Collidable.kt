package models

sealed interface Collidable {
    fun collide(collider: Collidable): Collidable
}
