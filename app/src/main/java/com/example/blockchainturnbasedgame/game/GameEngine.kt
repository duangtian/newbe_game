package com.example.blockchainturnbasedgame.game

import com.example.blockchainturnbasedgame.model.ActionType
import com.example.blockchainturnbasedgame.model.GameAction
import com.example.blockchainturnbasedgame.model.GameResult
import com.example.blockchainturnbasedgame.model.Player
import kotlin.random.Random

class GameEngine {
    private var player1: Player? = null
    private var player2: Player? = null
    private var currentTurn: Int = 1 // 1 for player1, 2 for player2
    private var gameActions: MutableList<GameAction> = mutableListOf()
    private var totalTurns: Int = 0
    private var isGameOver: Boolean = false
    
    fun initializeGame(player1Name: String, player2Name: String, 
                      player1Wallet: String? = null, player2Wallet: String? = null) {
        player1 = Player(1, player1Name, walletAddress = player1Wallet)
        player2 = Player(2, player2Name, walletAddress = player2Wallet)
        currentTurn = 1
        gameActions.clear()
        totalTurns = 0
        isGameOver = false
    }
    
    fun getCurrentPlayer(): Player? {
        return if (currentTurn == 1) player1 else player2
    }
    
    fun getOpponentPlayer(): Player? {
        return if (currentTurn == 1) player2 else player1
    }
    
    fun getCurrentTurn(): Int = currentTurn
    
    fun getPlayer1(): Player? = player1
    fun getPlayer2(): Player? = player2
    
    fun isGameFinished(): Boolean = isGameOver
    
    fun performAction(actionType: ActionType): GameActionResult {
        if (isGameOver) {
            return GameActionResult(false, "เกมจบแล้ว!")
        }
        
        val currentPlayer = getCurrentPlayer() ?: return GameActionResult(false, "ไม่พบผู้เล่น")
        val opponent = getOpponentPlayer() ?: return GameActionResult(false, "ไม่พบผู้เล่นคู่ต่อสู้")
        
        var message = ""
        var damage = 0
        
        when (actionType) {
            ActionType.ATTACK -> {
                damage = Random.nextInt(15, 26) // 15-25 damage
                val actualDamage = opponent.takeDamage(damage)
                message = "${currentPlayer.name} โจมตี ${opponent.name} ทำความเสียหาย $actualDamage แต้ม!"
            }
            
            ActionType.DEFEND -> {
                currentPlayer.defend()
                // Small heal when defending
                currentPlayer.heal(5)
                message = "${currentPlayer.name} ป้องกันและฟื้นฟูพลังชีวิต 5 แต้ม!"
            }
            
            ActionType.SPECIAL_ATTACK -> {
                if (currentPlayer.useSpecialAttack()) {
                    damage = Random.nextInt(25, 36) // 25-35 damage
                    val actualDamage = opponent.takeDamage(damage)
                    message = "${currentPlayer.name} ใช้โจมตีพิเศษ! ทำความเสียหาย $actualDamage แต้ม!"
                } else {
                    return GameActionResult(false, "ไม่มีพลังโจมตีพิเศษเหลือ!")
                }
            }
        }
        
        // Record the action
        val action = GameAction(currentPlayer.id, actionType, damage)
        gameActions.add(action)
        
        // Check if game is over
        if (!opponent.isAlive()) {
            isGameOver = true
            message += "\n🎉 ${currentPlayer.name} ชนะ!"
        } else {
            // Switch turns
            currentTurn = if (currentTurn == 1) 2 else 1
            totalTurns++
        }
        
        return GameActionResult(true, message)
    }
    
    fun getGameResult(): GameResult? {
        if (!isGameOver) return null
        
        val winner = if (player1?.isAlive() == true) player1 else player2
        val loser = if (player1?.isAlive() == false) player1 else player2
        
        return GameResult(winner, loser, totalTurns, gameActions.toList())
    }
    
    fun getBattleLog(): List<String> {
        return gameActions.map { action ->
            val player = if (action.playerId == 1) player1?.name else player2?.name
            when (action.actionType) {
                ActionType.ATTACK -> "⚔️ $player โจมตี (${action.damage} แต้ม)"
                ActionType.DEFEND -> "🛡️ $player ป้องกัน"
                ActionType.SPECIAL_ATTACK -> "💥 $player โจมตีพิเศษ (${action.damage} แต้ม)"
            }
        }
    }
}

data class GameActionResult(
    val success: Boolean,
    val message: String
)