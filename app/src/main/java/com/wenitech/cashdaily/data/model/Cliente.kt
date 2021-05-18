package com.wenitech.cashdaily.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ServerTimestamp

data class Cliente(
        @DocumentId
        val id: String? = null,
        @ServerTimestamp
        var creationDate: Timestamp? = null,
        var fechaCobro: Timestamp? = null,
        var fullName: String = "",
        val cedulaNumber: String = "",
        var gender: String = "",
        var phoneNumber: String = "",
        var city: String = "",
        var direction: String = "",
        var isCreditActive: Boolean = false,
        var documentReferenceCreditActive: DocumentReference? = null,
)