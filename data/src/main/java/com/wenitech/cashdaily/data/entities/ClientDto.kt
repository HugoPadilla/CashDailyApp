package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ServerTimestamp
import com.wenitech.cashdaily.domain.entities.Client

data class ClientDto(
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

fun ClientDto.toClient() = Client(
    id,
    creationDate?.toDate(),
    paymentDate?.toDate(),
    finishDate?.toDate(),
    idNumber,
    fullName,
    gender,
    phoneNumber,
    city,
    direction,
    creditActive,
    refCredit?.id,
)

fun toClientDto(client: Client): ClientDto = ClientDto(
    id = client.id,
    creationDate = client.creationDate?.let { Timestamp(it) },
    paymentDate = client.paymentDate?.let { Timestamp(it) },
    finishDate = client.finishDate?.let { Timestamp(it) },
    idNumber = client.idNumber,
    fullName = client.fullName,
    gender = client.gender,
    phoneNumber = client.phoneNumber,
    city = client.city,
    direction = client.direction,
    creditActive = client.creditActive,
    refCredit = null
)