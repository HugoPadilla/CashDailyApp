package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.*
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun createAccount(uid: String, user: User): Boolean

    // Application
    suspend fun getUserProfile(uid: String): Flow<Resource<User>>
    suspend fun getUserBox(uid: String): Flow<Resource<Box>>
    suspend fun getRecentMoves(uid: String): Flow<Resource<List<CashTransactions>>>
    suspend fun saveMoneyOnBox(
        uid: String,
        value: Double,
        description: String
    ): Flow<Resource<String>>

    // Client
    suspend fun getAllClientsPaging(): Flow<Resource<List<Client>>>
    suspend fun getClientsCollectToday(): Flow<Resource<List<Client>>>
    suspend fun getBackCustomers(): Flow<Resource<List<Client>>>
    suspend fun getOverdueCustomers(): Flow<Resource<List<Client>>>
    suspend fun saveNewClient(client: Client): Flow<Resource<String>>
    suspend fun updateClient(idClient: String, updateClient: Client): Flow<Resource<String>>
    suspend fun removeClient(idClient: String): Flow<Resource<String>>

    // Credit
    suspend fun getRecentCredits(uid: String, idClient: String): Flow<Resource<List<Credit>>>
    suspend fun getCreditClient(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<Resource<Credit>>

    suspend fun saveNewCredit(
        uid: String,
        idClient: String,
        newCredit: Credit
    ): Flow<Resource<String>>

    suspend fun getQuotaCredit(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<Resource<List<Quota>>>

    suspend fun saveNewQuota(
        uid: String,
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<Resource<String>>

    // Reports
    suspend fun getReports(): Flow<Resource<ReportsDaily>>
}