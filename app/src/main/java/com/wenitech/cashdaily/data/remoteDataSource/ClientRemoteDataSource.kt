package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Client
import kotlinx.coroutines.flow.Flow

interface ClientRemoteDataSource {

    suspend fun setNewClient(client: Client): Flow<Resource<String>>
    suspend fun updateClient(client: Client): Flow<Resource<String>>
    suspend fun removeClient(clientId: String): Flow<Resource<String>>

    suspend fun fetchAllClientsPaging(): Flow<Resource<List<Client>>>
    suspend fun getClientsCollectToday(): Flow<Resource<List<Client>>>
    suspend fun getBackCustomers(): Flow<Resource<List<Client>>>
    suspend fun getOverdueCustomers(): Flow<Resource<List<Client>>>

}