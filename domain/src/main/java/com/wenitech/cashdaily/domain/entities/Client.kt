package com.wenitech.cashdaily.domain.entities

import java.util.*

data class Client(
        val id: String? = null,
        val creationDate: Date? = null,
        val paymentDate: Date? = null,
        val finishDate: Date? = null,
        val idNumber: String = "",
        val fullName: String = "",
        val gender: String = "",
        val phoneNumber: String = "",
        val city: String = "",
        val direction: String = "",
        var creditActive: Boolean = false,
        val refCredit: String? = null,
)