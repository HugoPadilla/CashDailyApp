package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.ClientDto
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.flow.Flow

interface ClientRemoteDataSource {
    // Client
    suspend fun getAllClientsPaging(): Flow<Response<List<ClientDto>>>
    suspend fun getClientsCollectToday(): Flow<Response<List<ClientDto>>>
    suspend fun getClientById(idClient: String): Flow<Response<ClientDto>>
    suspend fun getBackCustomers(): Flow<Response<List<ClientDto>>>
    suspend fun getOverdueCustomers(): Flow<Response<List<ClientDto>>>
    suspend fun saveNewClient(clientDto: ClientDto): Flow<Response<String>>
    suspend fun updateClient(
        idClient: String,
        updateClientDto: ClientDto
    ): Flow<Response<String>>
    suspend fun removeClient(idClient: String): Flow<Response<String>>
}