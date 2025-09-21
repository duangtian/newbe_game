package com.example.blockchainturnbasedgame

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.blockchainturnbasedgame.blockchain.WalletManager
import com.example.blockchainturnbasedgame.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var walletManager: WalletManager
    private var currentWalletInfo: WalletManager.WalletInfo? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        walletManager = WalletManager()
        walletManager.initializeWeb3()
        
        setupUI()
        setupClickListeners()
    }
    
    private fun setupUI() {
        updateWalletUI()
        binding.btnStartGame.isEnabled = false
    }
    
    private fun setupClickListeners() {
        binding.btnConnectWallet.setOnClickListener {
            connectWallet()
        }
        
        binding.btnStartGame.setOnClickListener {
            startGame()
        }
    }
    
    private fun connectWallet() {
        binding.btnConnectWallet.isEnabled = false
        binding.tvStatus.text = "กำลังเชื่อมต่อ Wallet..."
        
        lifecycleScope.launch {
            try {
                // สำหรับ demo ใช้ mock wallet
                currentWalletInfo = walletManager.createMockWallet()
                
                currentWalletInfo?.let { walletInfo ->
                    if (walletInfo.isConnected) {
                        updateWalletUI()
                        binding.btnStartGame.isEnabled = true
                        binding.tvStatus.text = "เชื่อมต่อ Wallet สำเร็จ!"
                        Toast.makeText(this@MainActivity, "เชื่อมต่อ Wallet สำเร็จ!", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.tvStatus.text = "การเชื่อมต่อ Wallet ล้มเหลว"
                        Toast.makeText(this@MainActivity, "การเชื่อมต่อ Wallet ล้มเหลว", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                binding.tvStatus.text = "เกิดข้อผิดพลาด: ${e.message}"
                Toast.makeText(this@MainActivity, "เกิดข้อผิดพลาด: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                binding.btnConnectWallet.isEnabled = true
            }
        }
    }
    
    private fun updateWalletUI() {
        currentWalletInfo?.let { walletInfo ->
            if (walletInfo.isConnected) {
                val shortAddress = "${walletInfo.address.take(6)}...${walletInfo.address.takeLast(4)}"
                binding.tvWalletAddress.text = "Wallet: $shortAddress"
                binding.tvBalance.text = "ยอดคงเหลือ: ${walletInfo.balance} ETH"
                binding.btnConnectWallet.text = "เชื่อมต่อแล้ว ✓"
            } else {
                binding.tvWalletAddress.text = "Wallet: ไม่ได้เชื่อมต่อ"
                binding.tvBalance.text = "ยอดคงเหลือ: 0 ETH"
                binding.btnConnectWallet.text = getString(R.string.connect_wallet)
            }
        }
    }
    
    private fun startGame() {
        currentWalletInfo?.let { walletInfo ->
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("wallet_address", walletInfo.address)
                putExtra("wallet_balance", walletInfo.balance.toString())
            }
            startActivity(intent)
        }
    }
}