package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.wenitech.cashdaily.domain.constant.TypeAccountEnum
import com.wenitech.cashdaily.domain.entities.User

data class UserModel(
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
    val roles: List<String> = listOf("Admin"), // Todo: Establecer dinamicamente el rol del usuario
)

fun UserModel.toUserDomain() = User(
    id = id,
    timestampCreation = timestampCreation?.toDate(),
    businessName = businessName,
    email = email,
    isFullProfile = isFullProfile,
    typeAccount = typeAccount,
    fullName = fullName,
    urlPhoto = urlPhoto
)