package com.wenitech.cashdaily.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class MovimientoCaja(
        @DocumentId
        var id: String? = null,
        var valor: Double? = null,
        var descripcion: String? = null,
        var fecha: Timestamp? = null,
        var agregarDinero: Boolean? = null,
)
