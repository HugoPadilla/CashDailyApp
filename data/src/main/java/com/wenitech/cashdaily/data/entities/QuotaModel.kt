package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.wenitech.cashdaily.domain.entities.Quota

data class QuotaModel(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val timestamp: Timestamp? = null,
    val author: String = "",
    val value: Double = 0.0,
)

fun QuotaModel.toDomain() = Quota(
    id,
    timestamp?.toDate(),
    author,
    value
)

fun QuotaModel.toData(quota: Quota) = QuotaModel(
    id = quota.id,
    timestamp = Timestamp(quota.timestamp!!.time, 0),
    author = quota.author,
    value = quota.value
)