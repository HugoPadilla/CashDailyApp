package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.entities.CashTransactions
import kotlinx.coroutines.flow.Flow

interface BoxRepository {
    suspend fun getUserBox(): Flow<Resource<Box>>
    suspend fun getRecentMoves(): Flow<Resource<List<CashTransactions>>>
    suspend fun saveMoneyOnBox(value: Double, description: String): Flow<Resource<String>>
}