package com.wenitech.cashdaily.data.entities

import com.google.firebase.firestore.DocumentId
import com.wenitech.cashdaily.domain.entities.ReportMonth

data class ReportMonthDto(
        @DocumentId
        val id: String? = null,
)

fun ReportMonthDto.toReportMonth() = ReportMonth(
        id = id
)
