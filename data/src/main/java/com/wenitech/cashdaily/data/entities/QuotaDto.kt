package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.wenitech.cashdaily.domain.entities.Quota

data class QuotaDto(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val timestamp: Timestamp? = null,
    val author: String = "",
    val value: Double = 0.0,
)

fun QuotaDto.toQuota() = Quota(
    id,
    timestamp?.toDate(),
    author,
    value
)

fun toQuotaDto(quota: Quota) = QuotaDto(
    id = quota.id,
    timestamp = Timestamp(quota.timestamp!!.time, 0),
    author = quota.author,
    value = quota.value
)