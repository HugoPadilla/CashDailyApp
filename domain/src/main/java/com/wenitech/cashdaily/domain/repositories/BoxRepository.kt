package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.entities.CashTransactions
import kotlinx.coroutines.flow.Flow

interface BoxRepository {
    suspend fun getUserBox(): Flow<Response<Box>>
    suspend fun getRecentMoves(): Flow<Response<List<CashTransactions>>>
    suspend fun saveMoneyOnBox(value: Double, description: String): Flow<Response<String>>
}