package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class GetBackCustomersUseCase(
    private val repository: DataRepository
) {
    suspend operator fun invoke(): Flow<Response<List<Client>>> =
        repository.getBackCustomers()
}