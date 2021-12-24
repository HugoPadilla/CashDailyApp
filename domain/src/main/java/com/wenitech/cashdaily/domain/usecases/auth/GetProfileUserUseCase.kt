package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetProfileUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<Response<User>> {
        return userRepository.getUserProfile()
    }
}