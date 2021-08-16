package com.wenitech.cashdaily.domain.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.wenitech.cashdaily.domain.constant.TypeAccountEnum

data class User(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val timestampCreation: Timestamp? = null,
    val businessName: String = "",
    val email: String = "",
    var isFullProfile: Boolean = false,
    var typeAccount: String = TypeAccountEnum.Admin.name,
    val fullName: String = "",
    val urlPhoto: String = "",
)