package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class CashTransactionsModel(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val serverTimestamp: Timestamp? = null,
    val description: String = "",
    var isExpense: Boolean = false,
    val value: Double = 00.00,
)

fun CashTransactionsModel.toDomain() =
    com.wenitech.cashdaily.domain.entities.CashTransactions(
        id = id,
        serverTimestamp = serverTimestamp?.toDate(),
        description = description,
        isExpense = isExpense,
        value = value
    )
