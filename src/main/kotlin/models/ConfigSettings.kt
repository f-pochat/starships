package models

data class ConfigSettings(
    val numberOfPlayers: Int,
    val KeyMap: Map<String, Trigger>,
    val livesPerPlayer: Int,
    val gameKeyMap: Map<String, String>
    // val models: SpaceShipModels
)
