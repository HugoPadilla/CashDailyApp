package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.BoxModel
import com.wenitech.cashdaily.data.entities.CashTransactionsModel
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.flow.Flow

interface BoxRemoteDataSource {
    suspend fun getUserBox(): Flow<Response<BoxModel>>
    suspend fun getRecentMoves(): Flow<Response<List<CashTransactionsModel>>>
    suspend fun saveMoneyOnBox(value: Double, description: String): Flow<Response<String>>
}