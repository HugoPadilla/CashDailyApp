package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class GetClientById(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(idClient: String): Flow<Response<Client>>{
        return dataRepository.getClientById(idClient)
    }
}