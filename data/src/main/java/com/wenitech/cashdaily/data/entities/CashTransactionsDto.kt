package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.wenitech.cashdaily.domain.entities.CashTransactions

data class CashTransactionsDto(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val serverTimestamp: Timestamp? = null,
    val description: String = "",
    var isExpense: Boolean = false,
    val value: Double = 00.00,
)

fun CashTransactionsDto.toCashTransactions() = CashTransactions(
        id = id,
        serverTimestamp = serverTimestamp?.toDate(),
        description = description,
        isExpense = isExpense,
        value = value
    )
