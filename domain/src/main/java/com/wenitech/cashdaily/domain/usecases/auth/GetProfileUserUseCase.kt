package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class GetProfileUserUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(): Flow<Resource<User>> {
        return dataRepository.getUserProfile()
    }
}