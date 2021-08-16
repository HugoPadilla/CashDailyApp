package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveClientUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(client: Client): Flow<Resource<String>> {
        return dataRepository.saveNewClient(client)
    }
}