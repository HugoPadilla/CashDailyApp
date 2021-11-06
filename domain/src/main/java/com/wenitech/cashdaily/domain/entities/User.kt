package com.wenitech.cashdaily.domain.entities

import com.wenitech.cashdaily.domain.constant.TypeAccountEnum
import java.util.*

data class User(
    val id: String? = null,
    val timestampCreation: Date? = null,
    val businessName: String = "",
    val email: String = "",
    var isFullProfile: Boolean = false,
    var typeAccount: String = TypeAccountEnum.Admin.name,
    val fullName: String = "",
    val urlPhoto: String = "",
)