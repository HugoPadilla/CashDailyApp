package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.ClientModel
import kotlinx.coroutines.flow.Flow

interface ClientRemoteDataSource {

    suspend fun setNewClient(clientModel: ClientModel): Flow<com.wenitech.cashdaily.domain.common.Resource<String>>
    suspend fun updateClient(clientModel: ClientModel): Flow<com.wenitech.cashdaily.domain.common.Resource<String>>
    suspend fun removeClient(clientId: String): Flow<com.wenitech.cashdaily.domain.common.Resource<String>>

    suspend fun fetchAllClientsPaging(): Flow<com.wenitech.cashdaily.domain.common.Resource<List<ClientModel>>>
    suspend fun getClientsCollectToday(): Flow<com.wenitech.cashdaily.domain.common.Resource<List<ClientModel>>>
    suspend fun getBackCustomers(): Flow<com.wenitech.cashdaily.domain.common.Resource<List<ClientModel>>>
    suspend fun getOverdueCustomers(): Flow<com.wenitech.cashdaily.domain.common.Resource<List<ClientModel>>>

}