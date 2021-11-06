package com.wenitech.cashdaily.data.entities

data class BoxModel(
    val totalCash: Double = 00.00,
    var gastos: Double = 00.00
)

fun BoxModel.toDomain() = com.wenitech.cashdaily.domain.entities.Box(
    totalCash = totalCash,
    gastos = gastos
)