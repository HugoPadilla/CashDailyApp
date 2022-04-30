package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.wenitech.cashdaily.domain.constant.TypeRouteEnum
import com.wenitech.cashdaily.domain.entities.Ruta

data class RutaDto(
    @DocumentId
    val id: String? = null,
    @ServerTimestamp
    val serverTimestamp: Timestamp? = null,
    val authorId: String = "",
    val name: String = "",
    val numberOfCollector: Int = 0,
    val typeRoute: String = TypeRouteEnum.Personal.name
)

fun RutaDto.toRuta() = Ruta(
    id = id,
    serverTimestamp = serverTimestamp?.toDate(),
    authorId = authorId,
    name = name,
    numberOfCollector = numberOfCollector,
    typeRoute = typeRoute
)