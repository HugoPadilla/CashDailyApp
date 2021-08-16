package com.wenitech.cashdaily.domain.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class Quota(
        @DocumentId
        val id: String? = null,
        @ServerTimestamp
        val timestamp: Timestamp? = null,
        val author: String = "",
        val value: Double = 0.0,
)