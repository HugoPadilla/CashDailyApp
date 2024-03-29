package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.CreditModel
import com.wenitech.cashdaily.data.entities.QuotaDto
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.flow.Flow

interface CustomerCreditRemoteDataSource {
    // Credit
    suspend fun getRecentCredits(uid: String, idClient: String): Flow<Response<List<CreditModel>>>
    suspend fun getCreditClient(
        idClient: String,
        idCredit: String
    ): Flow<Response<CreditModel>>

    suspend fun saveNewCredit(
        idClient: String,
        newCreditModel: CreditModel
    ): Flow<Response<String>>

    suspend fun getQuotaCredit(
        idClient: String,
        idCredit: String
    ): Flow<Response<List<QuotaDto>>>

    suspend fun saveNewQuota(
        idClient: String,
        idCredit: String,
        newQuotaDto: QuotaDto,
    ): Flow<Response<String>>
}