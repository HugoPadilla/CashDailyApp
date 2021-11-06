package com.wenitech.cashdaily.domain.entities

import java.util.*

data class Quota(
        val id: String? = null,
        val timestamp: Date? = null,
        val author: String = "",
        val value: Double = 0.0,
)