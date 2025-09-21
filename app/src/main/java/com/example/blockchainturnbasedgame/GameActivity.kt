package com.example.blockchainturnbasedgame

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.blockchainturnbasedgame.blockchain.BlockchainGameManager
import com.example.blockchainturnbasedgame.databinding.ActivityGameBinding
import com.example.blockchainturnbasedgame.game.GameEngine
import com.example.blockchainturnbasedgame.model.ActionType
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityGameBinding
    private lateinit var gameEngine: GameEngine
    private lateinit var blockchainManager: BlockchainGameManager
    
    private var player1WalletAddress: String? = null
    private var battleLogText = StringBuilder()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // รับข้อมูลจาก Intent
        player1WalletAddress = intent.getStringExtra("wallet_address")
        val walletBalance = intent.getStringExtra("wallet_balance")
        
        gameEngine = GameEngine()
        blockchainManager = BlockchainGameManager()
        
        initializeGame()
        setupClickListeners()
        updateUI()
    }
    
    private fun initializeGame() {
        // สร้างเกมใหม่
        gameEngine.initializeGame(
            "ผู้เล่น 1", 
            "ผู้เล่น 2 (AI)",
            player1WalletAddress,
            null // AI ไม่มี wallet
        )
        
        addToBattleLog("🎮 เกมเริ่มต้นแล้ว!")
        addToBattleLog("ผู้เล่น 1 vs ผู้เล่น 2 (AI)")
        addToBattleLog("ตาแรกของ ผู้เล่น 1")
    }
    
    private fun setupClickListeners() {
        binding.btnAttack.setOnClickListener {
            performPlayerAction(ActionType.ATTACK)
        }
        
        binding.btnDefend.setOnClickListener {
            performPlayerAction(ActionType.DEFEND)
        }
        
        binding.btnSpecialAttack.setOnClickListener {
            performPlayerAction(ActionType.SPECIAL_ATTACK)
        }
        
        binding.btnEndGame.setOnClickListener {
            showEndGameDialog()
        }
    }
    
    private fun performPlayerAction(actionType: ActionType) {
        if (gameEngine.isGameFinished()) return
        
        // ปิดปุ่มชั่วคราว
        setActionButtonsEnabled(false)
        
        lifecycleScope.launch {
            try {
                // ผู้เล่น 1 ทำการกระทำ
                val result = gameEngine.performAction(actionType)
                if (result.success) {
                    addToBattleLog(result.message)
                    updateUI()
                    
                    // ตรวจสอบว่าเกมจบหรือยัง
                    if (gameEngine.isGameFinished()) {
                        handleGameEnd()
                        return@launch
                    }
                    
                    // รอสักครู่แล้วให้ AI เล่น
                    kotlinx.coroutines.delay(1000)
                    performAIAction()
                } else {
                    Toast.makeText(this@GameActivity, result.message, Toast.LENGTH_SHORT).show()
                    setActionButtonsEnabled(true)
                }
            } catch (e: Exception) {
                Toast.makeText(this@GameActivity, "เกิดข้อผิดพลาด: ${e.message}", Toast.LENGTH_LONG).show()
                setActionButtonsEnabled(true)
            }
        }
    }
    
    private suspend fun performAIAction() {
        if (gameEngine.isGameFinished()) return
        
        // AI เลือกการกระทำแบบสุ่ม
        val aiActions = listOf(ActionType.ATTACK, ActionType.DEFEND, ActionType.SPECIAL_ATTACK)
        val randomAction = aiActions.random()
        
        val result = gameEngine.performAction(randomAction)
        if (result.success) {
            addToBattleLog(result.message)
            updateUI()
            
            if (gameEngine.isGameFinished()) {
                handleGameEnd()
            } else {
                setActionButtonsEnabled(true)
            }
        }
    }
    
    private suspend fun handleGameEnd() {
        val gameResult = gameEngine.getGameResult()
        if (gameResult != null) {
            addToBattleLog("🎉 เกมจบแล้ว!")
            addToBattleLog("ผู้ชนะ: ${gameResult.winner?.name}")
            
            // บันทึกผลเกมลง blockchain
            addToBattleLog("🔗 กำลังบันทึกผลเกมลง blockchain...")
            
            val transactionResult = blockchainManager.recordGameResult(gameResult)
            if (transactionResult.success) {
                addToBattleLog("✅ บันทึกสำเร็จ!")
                addToBattleLog("Transaction: ${transactionResult.transactionHash?.take(10)}...")
                showGameEndDialog(gameResult.winner?.name ?: "ไม่ทราบ", transactionResult.transactionHash)
            } else {
                addToBattleLog("❌ การบันทึกล้มเหลว: ${transactionResult.message}")
                showGameEndDialog(gameResult.winner?.name ?: "ไม่ทราบ", null)
            }
        }
    }
    
    private fun updateUI() {
        val player1 = gameEngine.getPlayer1()
        val player2 = gameEngine.getPlayer2()
        val currentTurn = gameEngine.getCurrentTurn()
        
        // อัปเดตข้อมูลผู้เล่น
        player1?.let {
            binding.tvPlayer1Health.text = "❤️ ${it.health} HP"
            binding.tvPlayer1Status.text = when {
                !it.isAlive() -> "💀 พ่ายแพ้"
                it.isDefending -> "🛡️ ป้องกัน"
                else -> "⚔️ พร้อมรบ"
            }
        }
        
        player2?.let {
            binding.tvPlayer2Health.text = "❤️ ${it.health} HP"
            binding.tvPlayer2Status.text = when {
                !it.isAlive() -> "💀 พ่ายแพ้"
                it.isDefending -> "🛡️ ป้องกัน"
                else -> "⚔️ พร้อมรบ"
            }
        }
        
        // อัปเดตเทิร์น
        val currentPlayer = if (currentTurn == 1) "ผู้เล่น 1" else "ผู้เล่น 2 (AI)"
        binding.tvCurrentTurn.text = "ตาของ $currentPlayer"
        
        // อัปเดตสถานะปุ่ม
        val isPlayer1Turn = currentTurn == 1 && !gameEngine.isGameFinished()
        setActionButtonsEnabled(isPlayer1Turn)
        
        // อัปเดต battle log
        binding.tvBattleLog.text = battleLogText.toString()
        binding.battleLogScroll.post {
            binding.battleLogScroll.fullScroll(android.view.View.FOCUS_DOWN)
        }
    }
    
    private fun setActionButtonsEnabled(enabled: Boolean) {
        binding.btnAttack.isEnabled = enabled
        binding.btnDefend.isEnabled = enabled
        binding.btnSpecialAttack.isEnabled = enabled
        
        // ตรวจสอบ special attack charges
        if (enabled) {
            val player1 = gameEngine.getPlayer1()
            binding.btnSpecialAttack.isEnabled = (player1?.specialAttackCharges ?: 0) > 0
        }
    }
    
    private fun addToBattleLog(message: String) {
        val timestamp = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
            .format(java.util.Date())
        battleLogText.append("[$timestamp] $message\n")
    }
    
    private fun showGameEndDialog(winner: String, transactionHash: String?) {
        val message = StringBuilder().apply {
            append("🎉 เกมจบแล้ว!\n\n")
            append("ผู้ชนะ: $winner\n\n")
            if (transactionHash != null) {
                append("✅ บันทึกลง blockchain แล้ว\n")
                append("Transaction: ${transactionHash.take(16)}...\n\n")
            }
            append("ต้องการเล่นใหม่หรือไม่?")
        }
        
        AlertDialog.Builder(this)
            .setTitle("เกมจบแล้ว")
            .setMessage(message.toString())
            .setPositiveButton("เล่นใหม่") { _, _ ->
                restartGame()
            }
            .setNegativeButton("กลับหน้าหลัก") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun showEndGameDialog() {
        AlertDialog.Builder(this)
            .setTitle("จบเกม")
            .setMessage("ต้องการจบเกมและกลับหน้าหลักหรือไม่?")
            .setPositiveButton("ใช่") { _, _ ->
                finish()
            }
            .setNegativeButton("ยกเลิก", null)
            .show()
    }
    
    private fun restartGame() {
        battleLogText.clear()
        initializeGame()
        updateUI()
    }
}