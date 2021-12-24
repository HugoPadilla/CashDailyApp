package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.domain.entities.ReportsDaily
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    // Client
    suspend fun getAllClientsPaging(): Flow<Resource<List<Client>>>
    suspend fun getClientsCollectToday(): Flow<Resource<List<Client>>>
    suspend fun getBackCustomers(): Flow<Resource<List<Client>>>
    suspend fun getOverdueCustomers(): Flow<Resource<List<Client>>>
    suspend fun getClientById(idClient: String): Flow<Resource<Client>>
    suspend fun saveNewClient(client: Client): Flow<Resource<String>>
    suspend fun updateClient(idClient: String, client: Client): Flow<Resource<String>>
    suspend fun removeClient(idClient: String): Flow<Resource<String>>

    // CreditOfClient
    suspend fun getRecentCredits(uid: String, idClient: String): Flow<Resource<List<Credit>>>
    suspend fun getCreditClient(
        idClient: String,
        idCredit: String
    ): Flow<Resource<Credit>>

    suspend fun saveNewCredit(
        idClient: String,
        newCredit: Credit
    ): Flow<Resource<String>>

    suspend fun getQuotaCredit(
        idClient: String,
        idCredit: String
    ): Flow<Resource<List<Quota>>>

    suspend fun saveNewQuota(
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<Resource<String>>

    // Reports
    suspend fun getReports(): Flow<Resource<ReportsDaily>>
}