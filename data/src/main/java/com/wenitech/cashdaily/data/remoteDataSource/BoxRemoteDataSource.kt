package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.BoxModel
import com.wenitech.cashdaily.data.entities.CashTransactionsModel
import com.wenitech.cashdaily.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface BoxRemoteDataSource {
    suspend fun getUserBox(): Flow<Resource<BoxModel>>
    suspend fun getRecentMoves(): Flow<Resource<List<CashTransactionsModel>>>
    suspend fun saveMoneyOnBox(value: Double, description: String): Flow<Resource<String>>
}