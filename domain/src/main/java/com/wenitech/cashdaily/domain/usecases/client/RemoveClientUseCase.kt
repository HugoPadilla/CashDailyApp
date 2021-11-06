package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class RemoveClientUseCase(
    private val repository: DataRepository
) {
    suspend operator fun invoke(idClient: String): Flow<com.wenitech.cashdaily.domain.common.Resource<String>> =
        repository.removeClient(idClient)
}