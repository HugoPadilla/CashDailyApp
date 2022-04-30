package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.repositories.ClientRepository
import kotlinx.coroutines.flow.Flow

class GetClientsCollectToday(
    private val clientRepository: ClientRepository,
) {
    suspend operator fun invoke(): Flow<Response<List<Client>>> =
        clientRepository.getClientsCollectToday()
}