package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.entities.Quota
import kotlinx.coroutines.flow.Flow

interface CreditRepository {
    suspend fun deleteCustomerCredit(idClient: String, idCredit: String): Flow<Response<Void?>>
    suspend fun getRecentCredits(uid: String, idClient: String): Flow<Response<List<Credit>>>
    suspend fun getCreditClient(
        idClient: String,
        idCredit: String
    ): Flow<Response<Credit>>

    suspend fun saveNewCredit(
        idClient: String,
        newCredit: Credit
    ): Flow<Response<String>>

    suspend fun getQuotaCredit(
        idClient: String,
        idCredit: String
    ): Flow<Response<List<Quota>>>

    suspend fun saveNewQuota(
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<Response<String>>
}