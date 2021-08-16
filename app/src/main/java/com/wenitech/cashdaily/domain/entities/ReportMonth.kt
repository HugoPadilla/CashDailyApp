package com.wenitech.cashdaily.domain.entities

import com.google.firebase.firestore.DocumentId

data class ReportMonth(
        @DocumentId
        val id: String? = null,
)
