package com.wenitech.cashdaily.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class User(
        @DocumentId
        val id: String? = null,
        @ServerTimestamp
        val timesTamp: Timestamp? = null,
        @field: JvmField
        var isFullProfile: Boolean? = false,
        var nameBussine: String = "",
        var nameUser: String = "",
        var urlPhoto: String = "",
        var email: String = "",
        var isAdministrator: Boolean? = null,
)