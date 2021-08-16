package com.wenitech.cashdaily.domain.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class ReportsDaily(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val timestampCreation: Timestamp? = null,
    val timestampReport: Timestamp? = null,
    val prestamosRealizados: Int? = null,
    val inteteresesGenerados: Double? = null,
    val totalDineroPrestado: Double? = null,
)
