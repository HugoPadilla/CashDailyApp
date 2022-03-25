package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.ClientModel
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.flow.Flow

interface ClientRemoteDataSource {
    // Client
    suspend fun getAllClientsPaging(): Flow<Response<List<ClientModel>>>
    suspend fun getClientsCollectToday(): Flow<Response<List<ClientModel>>>
    suspend fun getClientById(idClient: String): Flow<Response<ClientModel>>
    suspend fun getBackCustomers(): Flow<Response<List<ClientModel>>>
    suspend fun getOverdueCustomers(): Flow<Response<List<ClientModel>>>
    suspend fun saveNewClient(clientModel: ClientModel): Flow<Response<String>>
    suspend fun updateClient(
        idClient: String,
        updateClientModel: ClientModel
    ): Flow<Response<String>>
    suspend fun removeClient(idClient: String): Flow<Response<String>>
}