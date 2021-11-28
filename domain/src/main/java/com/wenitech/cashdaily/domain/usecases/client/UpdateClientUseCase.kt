package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class UpdateClientUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(idClient: String, client: Client): Flow<Resource<String>> =
        dataRepository.updateClient(idClient, client)
}