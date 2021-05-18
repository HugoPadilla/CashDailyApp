package com.wenitech.cashdaily.features.customerCredit.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClientParcelable(
        val id: String,
        var isCreditActive: Boolean,
        var documentReferenceCreditActive: String? = null,
        var fullName: String,
        var identificationClient: String,
        var gender: String,
        var phoneNumber: String,
        var city: String,
        var direction: String
): Parcelable