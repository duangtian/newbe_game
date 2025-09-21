package com.example.blockchainturnbasedgame.blockchain

import android.util.Log
import com.example.blockchainturnbasedgame.model.GameResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger

/**
 * จัดการการบันทึกผลเกมลง blockchain
 * สำหรับ demo นี้จะใช้การจำลองการบันทึกข้อมูล
 */
class BlockchainGameManager {
    companion object {
        private const val TAG = "BlockchainGameManager"
    }
    
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    
    data class GameRecord(
        val gameId: String,
        val player1Address: String,
        val player2Address: String,
        val winnerAddress: String,
        val gameData: String,
        val timestamp: Long,
        val blockNumber: Long? = null,
        val transactionHash: String? = null
    )
    
    data class TransactionResult(
        val success: Boolean,
        val transactionHash: String?,
        val message: String
    )
    
    // จำลองการบันทึกผลเกมลง blockchain
    suspend fun recordGameResult(gameResult: GameResult): TransactionResult = withContext(Dispatchers.IO) {
        try {
            // สร้าง game record
            val gameId = generateGameId()
            val player1Address = gameResult.winner?.walletAddress ?: "0x0000000000000000000000000000000000000001"
            val player2Address = gameResult.loser?.walletAddress ?: "0x0000000000000000000000000000000000000002"
            val winnerAddress = gameResult.winner?.walletAddress ?: player1Address
            
            val gameRecord = GameRecord(
                gameId = gameId,
                player1Address = player1Address,
                player2Address = player2Address,
                winnerAddress = winnerAddress,
                gameData = gson.toJson(gameResult),
                timestamp = System.currentTimeMillis(),
                blockNumber = generateMockBlockNumber(),
                transactionHash = generateMockTransactionHash()
            )
            
            // จำลองการรอ confirmation
            kotlinx.coroutines.delay(2000) // รอ 2 วินาที
            
            Log.d(TAG, "Game result recorded: ${gameRecord.transactionHash}")
            Log.d(TAG, "Game data: ${gameRecord.gameData}")
            
            TransactionResult(
                success = true,
                transactionHash = gameRecord.transactionHash,
                message = "บันทึกผลเกมสำเร็จ!"
            )
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to record game result: ${e.message}")
            TransactionResult(
                success = false,
                transactionHash = null,
                message = "การบันทึกผลเกมล้มเหลว: ${e.message}"
            )
        }
    }
    
    // จำลองการดึงประวัติเกมจาก blockchain
    suspend fun getGameHistory(walletAddress: String): List<GameRecord> = withContext(Dispatchers.IO) {
        try {
            // จำลองข้อมูลประวัติเกม
            kotlinx.coroutines.delay(1000)
            
            val mockHistory = listOf(
                GameRecord(
                    gameId = "game_001",
                    player1Address = walletAddress,
                    player2Address = "0x742d35cc6084c1c4b32e7d2e3d2c18d5c3c8a8a8",
                    winnerAddress = walletAddress,
                    gameData = "Mock game data 1",
                    timestamp = System.currentTimeMillis() - 86400000, // 1 day ago
                    blockNumber = 12345678L,
                    transactionHash = "0x123456789abcdef123456789abcdef123456789abcdef123456789abcdef123456"
                ),
                GameRecord(
                    gameId = "game_002",
                    player1Address = "0x742d35cc6084c1c4b32e7d2e3d2c18d5c3c8a8a8",
                    player2Address = walletAddress,
                    winnerAddress = "0x742d35cc6084c1c4b32e7d2e3d2c18d5c3c8a8a8",
                    gameData = "Mock game data 2",
                    timestamp = System.currentTimeMillis() - 43200000, // 12 hours ago
                    blockNumber = 12345680L,
                    transactionHash = "0xabcdef123456789abcdef123456789abcdef123456789abcdef123456789abcdef"
                )
            )
            
            Log.d(TAG, "Retrieved ${mockHistory.size} game records for $walletAddress")
            mockHistory
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get game history: ${e.message}")
            emptyList()
        }
    }
    
    private fun generateGameId(): String {
        return "game_${System.currentTimeMillis()}_${(1000..9999).random()}"
    }
    
    private fun generateMockBlockNumber(): Long {
        return System.currentTimeMillis() / 1000 + (100000..999999).random()
    }
    
    private fun generateMockTransactionHash(): String {
        val chars = "0123456789abcdef"
        return "0x" + (1..64).map { chars.random() }.joinToString("")
    }
    
    // ตรวจสอบสถานะของธุรกรรม
    suspend fun getTransactionStatus(transactionHash: String): TransactionStatus = withContext(Dispatchers.IO) {
        try {
            // จำลองการตรวจสอบสถานะ
            kotlinx.coroutines.delay(500)
            
            TransactionStatus(
                hash = transactionHash,
                isConfirmed = true,
                blockNumber = generateMockBlockNumber(),
                confirmations = 12
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get transaction status: ${e.message}")
            TransactionStatus(
                hash = transactionHash,
                isConfirmed = false,
                blockNumber = null,
                confirmations = 0
            )
        }
    }
    
    data class TransactionStatus(
        val hash: String,
        val isConfirmed: Boolean,
        val blockNumber: Long?,
        val confirmations: Int
    )
}