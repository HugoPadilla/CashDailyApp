package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.wenitech.cashdaily.domain.constant.TypeRouteEnum

data class RutaModel(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val serverTimestamp: Timestamp? = null,
    val authorId: String = "",
    val name: String = "",
    val numberOfCollector: Int = 0,
    val typeRoute: String = TypeRouteEnum.Personal.name
)

fun RutaModel.toDomain() = com.wenitech.cashdaily.domain.entities.Ruta(
    id = id,
    serverTimestamp = serverTimestamp?.toDate(),
    authorId = authorId,
    name = name,
    numberOfCollector = numberOfCollector,
    typeRoute = typeRoute
)