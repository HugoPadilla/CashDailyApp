package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.ReportsDailyDto
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.flow.Flow

interface GeneralReportRemoteDataSource {
    suspend fun getReports(): Flow<Response<ReportsDailyDto>>
}