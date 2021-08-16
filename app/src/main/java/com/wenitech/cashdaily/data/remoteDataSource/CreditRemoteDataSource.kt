package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.entities.Quota
import kotlinx.coroutines.flow.Flow

interface CreditRemoteDataSource {
    // Credit
    suspend fun fetchRecentCreditClient(idClient: String): Flow<Resource<List<Credit>>>
    suspend fun getCreditClient(creditClientDocRef: String): Flow<Resource<Credit>>
    suspend fun saveNewCreditClientById(idClient: String, newCredit: Credit): Flow<Resource<String>>
    suspend fun getQuotaCredit(docRefCreditActive: String): Flow<Resource<List<Quota>>>
    suspend fun saveNewQuota(newQuota: Quota, idClient: String, referenceCredit: String): Flow<Resource<String>>
}