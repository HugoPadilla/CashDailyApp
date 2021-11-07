package com.wenitech.cashdaily.framework.DataExample

import com.wenitech.cashdaily.domain.entities.*
import java.util.*

val routesData = listOf(
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

val quotesListData = listOf(
    Quota(author = "Name author", value = 2400.00),
    Quota(author = "Name author", value = 2400.00),
    Quota(author = "Name author", value = 2400.00),
    Quota(author = "Name author", value = 2400.00),
    Quota(author = "Name author", value = 2400.00),
)

val creditData = listOf(
    Credit(
        amountFees = 30,
        creditDebt = 90000.00,
        creditValue = 100000.00,
        creditTotal = 120000.00,
        creditQuotaValue = 4000.00,
        paymentMethod = "Diario",
        percentage = 20
    )
)

val cashTransactionsData = listOf(
    CashTransactions(
        serverTimestamp = Date(),
        description = "Lorem ipsu dolor, me losen",
        value = 3000.00
    ),
    CashTransactions(
        serverTimestamp = Date(),
        description = "Lorem ipsu dolor, me losen",
        value = 3000.00
    ),
    CashTransactions(
        serverTimestamp = Date(),
        description = "Lorem ipsu dolor, me losen",
        value = 3000.00
    ),
    CashTransactions(
        serverTimestamp = Date(),
        description = "Lorem ipsu dolor, me losen",
        value = 3000.00
    ),
    CashTransactions(
        serverTimestamp = Date(),
        description = "Lorem ipsu dolor, me losen",
        value = 3000.00
    ),
    CashTransactions(
        serverTimestamp = Date(),
        description = "Lorem ipsu dolor, me losen",
        value = 3000.00
    ),
    CashTransactions(
        serverTimestamp = Date(),
        description = "Lorem ipsu dolor, me losen",
        value = 3000.00
    ),
)