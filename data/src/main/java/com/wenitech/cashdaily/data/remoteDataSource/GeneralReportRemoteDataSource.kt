package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.ReportsDailyModel
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.flow.Flow

interface GeneralReportRemoteDataSource {
    suspend fun getReports(): Flow<Response<ReportsDailyModel>>
}