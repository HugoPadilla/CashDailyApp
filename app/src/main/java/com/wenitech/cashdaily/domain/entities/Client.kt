package com.wenitech.cashdaily.domain.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ServerTimestamp

data class Client(
        @DocumentId
        val id: String? = null,
        @ServerTimestamp
        val creationDate: Timestamp? = null,
        val paymentDate: Timestamp? = null,
        val finishDate: Timestamp? = null,
        val idNumber: String = "",
        val fullName: String = "",
        val gender: String = "",
        val phoneNumber: String = "",
        val city: String = "",
        val direction: String = "",
        var creditActive: Boolean = false,
        val refCredit: DocumentReference? = null,
)