package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.ReportsDaily
import kotlinx.coroutines.flow.Flow

interface GeneralReportRepository {
    suspend fun getReports(): Flow<Response<ReportsDaily>>
}