package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.ReportsDailyDto
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GeneralReportRemoteDataSourceImp @Inject constructor() : GeneralReportRemoteDataSource {
    override suspend fun getReports(): Flow<Response<ReportsDailyDto>> {
        TODO("Not yet implemented")
    }
}