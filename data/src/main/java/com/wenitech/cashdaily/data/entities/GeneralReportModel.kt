package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class GeneralReportModel(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val timestampCreation: Timestamp? = null,
    val totalBalance: Double = 0.0,
    val totalPrestado: Double = 0.0,
    val totalGastos: Double = 0.0,
)

fun GeneralReportModel.toDomain() = com.wenitech.cashdaily.domain.entities.GeneralReport(
    id = id,
    timestampCreation = timestampCreation?.toDate(),
    totalBalance = totalBalance,
    totalPrestado = totalPrestado,
    totalGastos = totalGastos
)