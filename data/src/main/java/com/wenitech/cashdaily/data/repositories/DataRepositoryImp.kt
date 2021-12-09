package com.wenitech.cashdaily.data.repositories

import com.wenitech.cashdaily.data.entities.*
import com.wenitech.cashdaily.data.remoteDataSource.RemoteDataSource
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.*
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class DataRepositoryImp(
    private val remoteDataSource: RemoteDataSource,
    private val authRepository: AuthRepository
) : DataRepository {

    override suspend fun getUserProfile(): Flow<Resource<User>> {
        return remoteDataSource.getUserProfile().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.toUserDomain()))
            }
        }
    }

    override suspend fun getUserBox(): Flow<Resource<Box>> {
        return remoteDataSource.getUserBox().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.toDomain()))
            }
        }
    }

    override suspend fun getRecentMoves(): Flow<Resource<List<CashTransactions>>> {
        return remoteDataSource.getRecentMoves().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    override suspend fun saveMoneyOnBox(
        value: Double,
        description: String
    ): Flow<Resource<String>> {
        return remoteDataSource.saveMoneyOnBox(value, description)
    }

    override suspend fun getAllClientsPaging(): Flow<Resource<List<Client>>> {
        return remoteDataSource.getAllClientsPaging().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    /**
     * Devuelve los clientes que se tiene que cobrar el dia actual
     */
    override suspend fun getClientsCollectToday(): Flow<Resource<List<Client>>> {
        return remoteDataSource.getClientsCollectToday().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.map {
                    it.toDomain() }))
            }
        }
    }

    override suspend fun getBackCustomers(): Flow<Resource<List<Client>>> {
        return remoteDataSource.getBackCustomers().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(
                    Resource.Failure(
                        it.throwable,
                        it.msg
                    )
                )
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    override suspend fun getOverdueCustomers(): Flow<Resource<List<Client>>> {
        return remoteDataSource.getOverdueCustomers().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    override suspend fun getClientById(idClient: String): Flow<Resource<Client>> {
        return remoteDataSource.getClientById(idClient).transform {
            when(it){
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.toDomain()))
            }
        }
    }

    override suspend fun saveNewClient(client: Client): Flow<Resource<String>> {
        return remoteDataSource.saveNewClient(toData(client))
    }

    override suspend fun updateClient(
        idClient: String,
        client: Client
    ): Flow<Resource<String>> {
        return remoteDataSource.updateClient(idClient, toData(client))
    }

    override suspend fun removeClient(idClient: String): Flow<Resource<String>> {
        return remoteDataSource.removeClient(idClient)
    }

    override suspend fun getRecentCredits(
        uid: String,
        idClient: String
    ): Flow<Resource<List<Credit>>> {
        return remoteDataSource.getRecentCredits(uid, idClient).transform {
            when (it) {
                is Resource.Failure -> return@transform emit(
                    Resource.Failure(
                        it.throwable,
                        it.msg
                    )
                )
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    override suspend fun saveNewCredit(
        idClient: String,
        newCredit: Credit
    ): Flow<Resource<String>> {
        return remoteDataSource.saveNewCredit(idClient, CreditModel().toData(newCredit))
    }

    override suspend fun getCreditClient(
        idClient: String,
        idCredit: String
    ): Flow<Resource<Credit>> {
        return remoteDataSource.getCreditClient(idClient, idCredit).transform {

            when (it) {
                is Resource.Failure -> return@transform emit(
                    Resource.Failure(
                        it.throwable,
                        it.msg
                    )
                )
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.toDomain()))
            }
        }
    }

    override suspend fun getQuotaCredit(
        idClient: String,
        idCredit: String
    ): Flow<Resource<List<Quota>>> {
        return remoteDataSource.getQuotaCredit(idClient, idCredit).transform {
            when (it) {
                is Resource.Failure -> return@transform emit(
                    Resource.Failure(
                        it.throwable,
                        it.msg
                    )
                )
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    override suspend fun saveNewQuota(
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<Resource<String>> {
        return remoteDataSource.saveNewQuota(idClient, idCredit, QuotaModel(value = newQuota.value))
    }

    override suspend fun getReports(): Flow<Resource<ReportsDaily>> {
        return remoteDataSource.getReports().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.toDomain()))
            }
        }
    }
}