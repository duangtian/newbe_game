package com.example.blockchainturnbasedgame.model

enum class ActionType {
    ATTACK,
    DEFEND,
    SPECIAL_ATTACK
}

data class GameAction(
    val playerId: Int,
    val actionType: ActionType,
    val damage: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

data class GameResult(
    val winner: Player?,
    val loser: Player?,
    val totalTurns: Int,
    val gameActions: List<GameAction>
)