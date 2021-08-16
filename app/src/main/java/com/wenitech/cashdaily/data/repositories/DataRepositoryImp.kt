package com.wenitech.cashdaily.data.repositories

import com.wenitech.cashdaily.data.remoteDataSource.RemoteDataSource
import com.wenitech.cashdaily.domain.entities.*
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val authRepository: AuthRepository
    // private val clientDao: ClientDao
) : DataRepository {

    override suspend fun getUserProfile(uid: String): Flow<Resource<User>> {
        return remoteDataSource.getUserProfile(uid)
    }

    override suspend fun getUserBox(uid: String): Flow<Resource<Box>> {
        return remoteDataSource.getUserBox(uid)
    }

    override suspend fun getRecentMoves(uid: String): Flow<Resource<List<CashTransactions>>> {
        return remoteDataSource.getRecentMoves(uid)
    }

    override suspend fun saveMoneyOnBox(
        uid: String,
        value: Double,
        description: String
    ): Flow<Resource<String>> {
        return remoteDataSource.saveMoneyOnBox(uid, value, description)
    }

    override suspend fun getAllClientsPaging(): Flow<Resource<List<Client>>> {
        return remoteDataSource.getAllClientsPaging()
    }

    override suspend fun getClientsCollectToday(): Flow<Resource<List<Client>>> {
        return remoteDataSource.getClientsCollectToday()
    }

    override suspend fun getBackCustomers(): Flow<Resource<List<Client>>> {
        return remoteDataSource.getBackCustomers()
    }

    override suspend fun getOverdueCustomers(): Flow<Resource<List<Client>>> {
        return remoteDataSource.getOverdueCustomers()
    }

    override suspend fun saveNewClient(client: Client): Flow<Resource<String>> {
        return remoteDataSource.saveNewClient(client)
    }

    override suspend fun updateClient(idClient: String, client: Client): Flow<Resource<String>> {
        return remoteDataSource.updateClient(idClient, client)
    }

    override suspend fun removeClient(idClient: String): Flow<Resource<String>> {
        return remoteDataSource.removeClient(idClient)
    }

    override suspend fun getRecentCredits(
        uid: String,
        idClient: String
    ): Flow<Resource<List<Credit>>> {
        return remoteDataSource.getRecentCredits(uid, idClient)
    }

    override suspend fun saveNewCredit(
        uid: String,
        idClient: String,
        newCredit: Credit
    ): Flow<Resource<String>> {
        return remoteDataSource.saveNewCredit(uid, idClient, newCredit)
    }

    override suspend fun getCreditClient(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<Resource<Credit>> {
        return remoteDataSource.getCreditClient(uid, idClient, idCredit)
    }

    override suspend fun getQuotaCredit(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<Resource<List<Quota>>> {
        return remoteDataSource.getQuotaCredit(uid, idClient, idCredit)
    }

    override suspend fun saveNewQuota(
        uid: String,
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<Resource<String>> {
        return remoteDataSource.saveNewQuota(uid, idClient, idCredit, newQuota)
    }

    override suspend fun getReports(): Flow<Resource<ReportsDaily>> {
        return remoteDataSource.getReports()
    }
}