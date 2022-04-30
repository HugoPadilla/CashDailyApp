package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.repositories.ClientRepository
import kotlinx.coroutines.flow.Flow

class UpdateClientUseCase(
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(idClient: String, client: Client): Flow<Response<String>> =
        clientRepository.updateClient(idClient, client)
}