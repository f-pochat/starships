package models

data class Collider(val collidable: Collidable) {
    fun collide(collider: Collider): Collider = copy(collidable = collidable.collide(collider.collidable))
}
