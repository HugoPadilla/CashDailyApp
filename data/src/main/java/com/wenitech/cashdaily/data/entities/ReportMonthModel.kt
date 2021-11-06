package com.wenitech.cashdaily.data.entities

import com.google.firebase.firestore.DocumentId

data class ReportMonthModel(
        @DocumentId
        val id: String? = null,
)

fun ReportMonthModel.toDomain() = com.wenitech.cashdaily.domain.entities.ReportMonth(
        id = id
)
