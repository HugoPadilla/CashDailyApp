package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.CreditModel
import com.wenitech.cashdaily.data.entities.QuotaModel
import kotlinx.coroutines.flow.Flow

interface CreditRemoteDataSource {
    // Credit
    suspend fun fetchRecentCreditClient(idClient: String): Flow<com.wenitech.cashdaily.domain.common.Resource<List<CreditModel>>>
    suspend fun getCreditClient(creditClientDocRef: String): Flow<com.wenitech.cashdaily.domain.common.Resource<CreditModel>>
    suspend fun saveNewCreditClientById(idClient: String, newCreditModel: CreditModel): Flow<com.wenitech.cashdaily.domain.common.Resource<String>>
    suspend fun getQuotaCredit(docRefCreditActive: String): Flow<com.wenitech.cashdaily.domain.common.Resource<List<QuotaModel>>>
    suspend fun saveNewQuota(newQuotaModel: QuotaModel, idClient: String, referenceCredit: String): Flow<com.wenitech.cashdaily.domain.common.Resource<String>>
}