package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Client
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    // Client
    suspend fun getAllClientsPaging(): Flow<Response<List<Client>>>
    suspend fun getClientsCollectToday(): Flow<Response<List<Client>>>
    suspend fun getBackCustomers(): Flow<Response<List<Client>>>
    suspend fun getOverdueCustomers(): Flow<Response<List<Client>>>
    suspend fun getClientById(idClient: String): Flow<Response<Client>>
    suspend fun saveNewClient(client: Client): Flow<Response<String>>
    suspend fun updateClient(idClient: String, client: Client): Flow<Response<String>>
    suspend fun removeClient(idClient: String): Flow<Response<String>>

}