package com.wenitech.cashdaily.domain.entities

import java.util.*

data class ReportsDaily(
    val id: String? = null,
    val timestampCreation: Date? = null,
    val timestampReport: Date? = null,
    val prestamosRealizados: Int? = null,
    val inteteresesGenerados: Double? = null,
    val totalDineroPrestado: Double? = null,
)
