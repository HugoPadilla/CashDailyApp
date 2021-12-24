package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetProfileUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<Resource<User>> {
        return userRepository.getUserProfile()
    }
}