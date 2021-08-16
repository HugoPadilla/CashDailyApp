package com.wenitech.cashdaily.domain.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class CashTransactions(
        @DocumentId
        val id: String? = null,
        @ServerTimestamp
        val serverTimestamp: Timestamp? = null,
        val description: String = "",
        var isExpense: Boolean = false,
        val value: Double = 00.00,
)
