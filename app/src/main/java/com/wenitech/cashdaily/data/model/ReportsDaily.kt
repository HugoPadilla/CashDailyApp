package com.wenitech.cashdaily.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class ReportsDaily(
        @DocumentId
        val documentId: String? = null,
        val timestampReport: Timestamp? = null,
        val prestamosRealizados: Int? = null,
        val inteteresesGenerados: Double? = null,
        val totalDineroPrestado: Double? = null,
        
)
