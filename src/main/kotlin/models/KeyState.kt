package models

enum class KeyState
(private val action: (GameState) -> GameState) : Trigger {
    PAUSE({ s: GameState -> s.copy(state = PAUSE) }),
    PLAY({ s: GameState -> s.copy(state = PLAY) });

    fun apply(gs: GameState): GameState {
        return action.invoke(gs)
    }
}
