package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.repositories.ClientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllClientsPagingUseCase @Inject constructor(
    private val clientRepository: ClientRepository,
) {
    suspend operator fun invoke(): Flow<Response<List<Client>>> =
        clientRepository.getAllClientsPaging()
}