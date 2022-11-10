package models

data class GameState(
    val state: KeyState,
    val movables: List<Movable>
) {
    fun movements(): GameState = copy(movables = movables.map { it.move() })
}
