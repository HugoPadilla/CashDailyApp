package com.wenitech.cashdaily.data.repositories

import com.wenitech.cashdaily.data.entities.toDomain
import com.wenitech.cashdaily.data.remoteDataSource.GeneralReportRemoteDataSource
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.ReportsDaily
import com.wenitech.cashdaily.domain.repositories.GeneralReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GeneralReportRepositoryImpl @Inject constructor(
    private val generalReportRemoteDataSource: GeneralReportRemoteDataSource
) : GeneralReportRepository {
    override suspend fun getReports(): Flow<Response<ReportsDaily>> {
        return generalReportRemoteDataSource.getReports().transform {
            when (it) {
                is Response.Error -> return@transform emit(Response.Error(it.throwable, it.msg))
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.toDomain()))
            }
        }
    }
}