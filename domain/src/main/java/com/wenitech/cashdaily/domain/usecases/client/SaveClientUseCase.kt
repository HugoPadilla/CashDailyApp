package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class SaveClientUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(client: Client): Flow<com.wenitech.cashdaily.domain.common.Resource<String>> {
        return dataRepository.saveNewClient(client)
    }
}