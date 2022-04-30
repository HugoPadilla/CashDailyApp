package com.wenitech.cashdaily.data.entities

import com.wenitech.cashdaily.domain.entities.Box

data class BoxDto(
    val totalCash: Double = 00.00,
    var gastos: Double = 00.00
)

fun BoxDto.toBox() = Box(
    totalCash = totalCash,
    gastos = gastos
)