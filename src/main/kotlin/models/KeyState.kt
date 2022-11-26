package models

import services.GameService

enum class KeyState
(private val action: (GameService) -> GameService) : Trigger {
    PAUSE({ s: GameService -> s.copy(state = PAUSE) }),
    PLAY({ s: GameService -> s.copy(state = PLAY) });

    fun apply(gs: GameService): GameService {
        return action.invoke(gs)
    }
}
