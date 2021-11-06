package com.wenitech.cashdaily.domain.entities

import java.util.*

data class GeneralReport(
    val id: String? = null,
    val timestampCreation: Date? = null,
    val totalBalance: Double = 0.0,
    val totalPrestado: Double = 0.0,
    val totalGastos: Double = 0.0,
)
