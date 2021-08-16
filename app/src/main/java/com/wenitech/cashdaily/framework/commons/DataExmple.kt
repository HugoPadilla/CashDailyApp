package com.wenitech.cashdaily.framework.commons

import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.entities.Ruta

val rutasData = listOf(
    Ruta(name = "Maren Lubin", authorId = "Menlo Park, CA"),
    Ruta(name = "Leslie Alexander", authorId = "Kalamazoo, MI"),
    Ruta(name = "Robert Fox", authorId = "Poway, CA"),
    Ruta(name = "Marvin McKinney", authorId = "San Luis Obispo, CA"),
    Ruta(name = "Esther Howard", authorId = "Manhattan, NY"),
    Ruta(name = "Ralph Edwards", authorId = "Bakersfield, CA"),
)
val clientsData = listOf(
    Client(fullName = "Maren Lubin", city = "Menlo Park", direction = "CA"),
    Client(fullName = "Leslie Alexander", city = "Kalamazoo", direction = "MI"),
    Client(fullName = "Robert Fox", city = "Poway", direction = "CA"),
    Client(fullName = "Marvin McKinney", city = "San Luis Obispo", direction = "CA"),
    Client(fullName = "Esther Howard", city = "Manhattan", direction = "NY"),
    Client(fullName = "Ralph Edwards", city = "Bakersfield", direction = "CA")
)