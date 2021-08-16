package com.wenitech.cashdaily.domain.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.wenitech.cashdaily.domain.constant.TypeRouteEnum

data class Ruta(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val serverTimestamp: Timestamp? = null,
    val authorId: String = "",
    val name: String = "",
    val numberOfCollector: Int = 0,
    val typeRoute: String = TypeRouteEnum.Personal.name
)
