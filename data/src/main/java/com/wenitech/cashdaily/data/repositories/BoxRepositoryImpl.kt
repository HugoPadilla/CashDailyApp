package com.wenitech.cashdaily.data.repositories

import com.wenitech.cashdaily.data.entities.toBox
import com.wenitech.cashdaily.data.entities.toCashTransactions
import com.wenitech.cashdaily.data.remoteDataSource.BoxRemoteDataSource
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.domain.repositories.BoxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class BoxRepositoryImpl @Inject constructor(
    private val boxRemoteDataSource: BoxRemoteDataSource
) : BoxRepository {
    override suspend fun getUserBox(): Flow<Response<Box>> {
        return boxRemoteDataSource.getUserBox().transform {
            when (it) {
                is Response.Error -> return@transform emit(Response.Error(it.throwable, it.msg))
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.toBox()))
            }
        }
    }

    override suspend fun getRecentMoves(): Flow<Response<List<CashTransactions>>> {
        return boxRemoteDataSource.getRecentMoves().transform { response ->
            when (response) {
                is Response.Error -> return@transform emit(
                    Response.Error(
                        response.throwable,
                        response.msg
                    )
                )
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(response.data.map { it.toCashTransactions() }))
            }
        }
    }

    override suspend fun saveMoneyOnBox(
        value: Double,
        description: String
    ): Flow<Response<String>> {
        return boxRemoteDataSource.saveMoneyOnBox(value, description)
    }
}