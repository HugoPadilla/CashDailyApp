package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.repositories.ClientRepository
import kotlinx.coroutines.flow.Flow

class RemoveClientUseCase(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(idClient: String): Flow<Response<String>> =
        repository.removeClient(idClient)
}