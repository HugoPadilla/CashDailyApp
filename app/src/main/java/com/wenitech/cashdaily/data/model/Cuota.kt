package com.wenitech.cashdaily.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Cuota(
        @DocumentId
        val id: String? = null,
        var fechaCreacion: Timestamp? = null,
        var nombreCreacion: String? = null,
        var estadoEditado: String? = null,
        var valorCuota: Double? = null,
)