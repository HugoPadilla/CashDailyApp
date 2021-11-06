package com.wenitech.cashdaily.domain.entities

import java.util.*

data class CashTransactions(
        val id: String? = null,
        val serverTimestamp: Date? = null,
        val description: String = "",
        var isExpense: Boolean = false,
        val value: Double = 00.00,
)
