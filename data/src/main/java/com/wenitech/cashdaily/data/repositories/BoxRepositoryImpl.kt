package com.wenitech.cashdaily.data.repositories

import com.wenitech.cashdaily.data.entities.toDomain
import com.wenitech.cashdaily.data.remoteDataSource.BoxRemoteDataSource
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.domain.repositories.BoxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class BoxRepositoryImpl(private val boxRemoteDataSource: BoxRemoteDataSource): BoxRepository {
    override suspend fun getUserBox(): Flow<Resource<Box>> {
        return boxRemoteDataSource.getUserBox().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.toDomain()))
            }
        }
    }

    override suspend fun getRecentMoves(): Flow<Resource<List<CashTransactions>>> {
        return boxRemoteDataSource.getRecentMoves().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    override suspend fun saveMoneyOnBox(
        value: Double,
        description: String
    ): Flow<Resource<String>> {
        return boxRemoteDataSource.saveMoneyOnBox(value, description)
    }
}