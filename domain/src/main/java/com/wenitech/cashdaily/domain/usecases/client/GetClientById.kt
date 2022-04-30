package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.repositories.ClientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClientById @Inject constructor(
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(idClient: String): Flow<Response<Client>> {
        return clientRepository.getClientById(idClient)
    }
}