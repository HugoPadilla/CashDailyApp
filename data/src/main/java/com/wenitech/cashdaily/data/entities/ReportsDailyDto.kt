package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.wenitech.cashdaily.domain.entities.ReportsDaily

data class ReportsDailyDto(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val timestampCreation: Timestamp? = null,
    val timestampReport: Timestamp? = null,
    val prestamosRealizados: Int? = null,
    val inteteresesGenerados: Double? = null,
    val totalDineroPrestado: Double? = null,
)

fun ReportsDailyDto.toReportsDaily() = ReportsDaily(
    id = id,
    timestampCreation = timestampCreation?.toDate(),
    timestampReport = timestampReport?.toDate(),
    prestamosRealizados = prestamosRealizados,
    inteteresesGenerados = inteteresesGenerados,
    totalDineroPrestado = totalDineroPrestado
)
