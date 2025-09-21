package com.example.blockchainturnbasedgame.blockchain

import android.util.Log
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WalletManager {
    companion object {
        private const val TAG = "WalletManager"
        // ใช้ Sepolia testnet สำหรับการทดสอบ
        private const val SEPOLIA_RPC_URL = "https://sepolia.infura.io/v3/YOUR_INFURA_PROJECT_ID"
    }
    
    private var web3j: Web3j? = null
    private var credentials: Credentials? = null
    
    data class WalletInfo(
        val address: String,
        val balance: BigDecimal,
        val isConnected: Boolean
    )
    
    fun initializeWeb3() {
        try {
            // สำหรับการทดสอบ ใช้ local blockchain หรือ testnet
            web3j = Web3j.build(HttpService("http://127.0.0.1:8545")) // Local Ganache
            Log.d(TAG, "Web3j initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Web3j: ${e.message}")
        }
    }
    
    suspend fun createWallet(): WalletInfo = withContext(Dispatchers.IO) {
        try {
            // สร้าง wallet ใหม่
            val ecKeyPair = Keys.createEcKeyPair()
            credentials = Credentials.create(ecKeyPair)
            
            val address = credentials?.address ?: ""
            Log.d(TAG, "Created new wallet: $address")
            
            val balance = getBalance(address)
            WalletInfo(address, balance, true)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create wallet: ${e.message}")
            WalletInfo("", BigDecimal.ZERO, false)
        }
    }
    
    suspend fun connectWallet(privateKey: String): WalletInfo = withContext(Dispatchers.IO) {
        try {
            credentials = Credentials.create(privateKey)
            val address = credentials?.address ?: ""
            val balance = getBalance(address)
            
            Log.d(TAG, "Connected to wallet: $address")
            WalletInfo(address, balance, true)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to connect wallet: ${e.message}")
            WalletInfo("", BigDecimal.ZERO, false)
        }
    }
    
    suspend fun getBalance(address: String): BigDecimal = withContext(Dispatchers.IO) {
        try {
            web3j?.let { web3 ->
                val ethGetBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send()
                val balanceWei = ethGetBalance.balance
                Convert.fromWei(BigDecimal(balanceWei), Convert.Unit.ETHER)
            } ?: BigDecimal.ZERO
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get balance: ${e.message}")
            BigDecimal.ZERO
        }
    }
    
    fun getCurrentWalletAddress(): String? {
        return credentials?.address
    }
    
    fun isWalletConnected(): Boolean {
        return credentials != null
    }
    
    // สำหรับการทดสอบ - สร้าง mock wallet
    fun createMockWallet(): WalletInfo {
        val mockAddress = "0x" + (1..40).map { "0123456789abcdef".random() }.joinToString("")
        val mockBalance = BigDecimal("1.5") // 1.5 ETH for testing
        
        Log.d(TAG, "Created mock wallet: $mockAddress")
        return WalletInfo(mockAddress, mockBalance, true)
    }
}