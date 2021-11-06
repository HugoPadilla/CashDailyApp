package com.wenitech.cashdaily.domain.entities

import com.wenitech.cashdaily.domain.constant.TypeRouteEnum
import java.util.*

data class Ruta(
    val id: String? = null,
    val serverTimestamp: Date? = null,
    val authorId: String = "",
    val name: String = "",
    val numberOfCollector: Int = 0,
    val typeRoute: String = TypeRouteEnum.Personal.name
)
