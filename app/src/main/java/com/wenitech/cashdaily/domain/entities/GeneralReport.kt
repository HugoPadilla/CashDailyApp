package com.wenitech.cashdaily.domain.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class GeneralReport(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val timestampCreation: Timestamp? = null,
    val totalBalance: Double = 0.0,
    val totalPrestado: Double = 0.0,
    val totalGastos: Double = 0.0,
)
