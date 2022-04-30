package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.BoxDto
import com.wenitech.cashdaily.data.entities.CashTransactionsDto
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.flow.Flow

interface BoxRemoteDataSource {
    fun getUserBox(): Flow<Response<BoxDto>>
    suspend fun getRecentMoves(): Flow<Response<List<CashTransactionsDto>>>
    suspend fun saveMoneyOnBox(value: Double, description: String): Flow<Response<String>>
}