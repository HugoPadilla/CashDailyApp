package com.wenitech.cashdaily.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

class Credito(
        @DocumentId
        var id: String? = null,
        var fechaPretamo: Timestamp? = null,
        var fechaProximaCuota: Timestamp? = null,
        var modalida: String? = null,
        var valorPrestamo: Double? = null,
        var valorCuota: Double? = null,
        var porcentaje: Double? = null,
        var totalPrestamo: Double? = null,
        var deudaPrestamo: Double? = null,
        var numeroCuotas: Int? = null,
        var isNoCobrarSabados: Boolean? = null,
        var isNoCobrarDomingos: Boolean? = null,
)