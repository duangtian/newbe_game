package com.example.blockchainturnbasedgame.model

data class Player(
    val id: Int,
    val name: String,
    var health: Int = 100,
    var maxHealth: Int = 100,
    var isDefending: Boolean = false,
    var specialAttackCharges: Int = 3,
    val walletAddress: String? = null
) {
    fun isAlive(): Boolean = health > 0
    
    fun takeDamage(damage: Int): Int {
        val actualDamage = if (isDefending) damage / 2 else damage
        health = maxOf(0, health - actualDamage)
        isDefending = false // Reset defending status after taking damage
        return actualDamage
    }
    
    fun heal(amount: Int) {
        health = minOf(maxHealth, health + amount)
    }
    
    fun defend() {
        isDefending = true
    }
    
    fun useSpecialAttack(): Boolean {
        return if (specialAttackCharges > 0) {
            specialAttackCharges--
            true
        } else {
            false
        }
    }
    
    fun getHealthPercentage(): Float {
        return health.toFloat() / maxHealth.toFloat()
    }
}