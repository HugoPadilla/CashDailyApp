package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.*
import com.wenitech.cashdaily.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun createAccount(userModel: UserModel): Boolean

    // Application
    suspend fun getUserProfile(): Flow<Resource<UserModel>>
    suspend fun getUserBox(): Flow<Resource<BoxModel>>
    suspend fun getRecentMoves(): Flow<Resource<List<CashTransactionsModel>>>
    suspend fun saveMoneyOnBox(
        value: Double,
        description: String
    ): Flow<Resource<String>>

    // Client
    suspend fun getAllClientsPaging(): Flow<Resource<List<ClientModel>>>
    suspend fun getClientsCollectToday(): Flow<Resource<List<ClientModel>>>
    suspend fun getBackCustomers(): Flow<Resource<List<ClientModel>>>
    suspend fun getOverdueCustomers(): Flow<Resource<List<ClientModel>>>
    suspend fun saveNewClient(clientModel: ClientModel): Flow<Resource<String>>
    suspend fun updateClient(idClient: String, updateClientModel: ClientModel): Flow<Resource<String>>
    suspend fun removeClient(idClient: String): Flow<Resource<String>>

    // Credit
    suspend fun getRecentCredits(uid: String, idClient: String): Flow<Resource<List<CreditModel>>>
    suspend fun getCreditClient(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<Resource<CreditModel>>

    suspend fun saveNewCredit(
        uid: String,
        idClient: String,
        newCreditModel: CreditModel
    ): Flow<Resource<String>>

    suspend fun getQuotaCredit(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<Resource<List<QuotaModel>>>

    suspend fun saveNewQuota(
        uid: String,
        idClient: String,
        idCredit: String,
        newQuotaModel: QuotaModel,
    ): Flow<Resource<String>>

    // Reports
    suspend fun getReports(): Flow<Resource<ReportsDailyModel>>
}