package com.wenitech.cashdaily.domain.usecases.client

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBackCustomersUseCase @Inject constructor(
    private val repository: DataRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Client>>> =
        repository.getBackCustomers()
}