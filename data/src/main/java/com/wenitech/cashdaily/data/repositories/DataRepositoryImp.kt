package com.wenitech.cashdaily.data.repositories

import com.wenitech.cashdaily.data.entities.*
import com.wenitech.cashdaily.data.remoteDataSource.ClientRemoteDataSource
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.*
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class DataRepositoryImp(
    private val clientRemoteDataSource: ClientRemoteDataSource,
) : DataRepository {

    override suspend fun getAllClientsPaging(): Flow<Response<List<Client>>> {
        return clientRemoteDataSource.getAllClientsPaging().transform {
            when (it) {
                is Response.Error -> return@transform emit(Response.Error(it.throwable, it.msg))
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    /**
     * Devuelve los clientes que se tiene que cobrar el dia actual
     */
    override suspend fun getClientsCollectToday(): Flow<Response<List<Client>>> {
        return clientRemoteDataSource.getClientsCollectToday().transform {
            when (it) {
                is Response.Error -> return@transform emit(Response.Error(it.throwable, it.msg))
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.map {
                    it.toDomain() }))
            }
        }
    }

    override suspend fun getBackCustomers(): Flow<Response<List<Client>>> {
        return clientRemoteDataSource.getBackCustomers().transform {
            when (it) {
                is Response.Error -> return@transform emit(
                    Response.Error(
                        it.throwable,
                        it.msg
                    )
                )
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    override suspend fun getOverdueCustomers(): Flow<Response<List<Client>>> {
        return clientRemoteDataSource.getOverdueCustomers().transform {
            when (it) {
                is Response.Error -> return@transform emit(Response.Error(it.throwable, it.msg))
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    override suspend fun getClientById(idClient: String): Flow<Response<Client>> {
        return clientRemoteDataSource.getClientById(idClient).transform {
            when(it){
                is Response.Error -> return@transform emit(Response.Error(it.throwable, it.msg))
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.toDomain()))
            }
        }
    }

    override suspend fun saveNewClient(client: Client): Flow<Response<String>> {
        return clientRemoteDataSource.saveNewClient(toData(client))
    }

    override suspend fun updateClient(
        idClient: String,
        client: Client
    ): Flow<Response<String>> {
        return clientRemoteDataSource.updateClient(idClient, toData(client))
    }

    override suspend fun removeClient(idClient: String): Flow<Response<String>> {
        return clientRemoteDataSource.removeClient(idClient)
    }
}