package com.wenitech.cashdaily.domain.usecases.user

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Response<User>> {
        return userRepository.getUserProfile()
    }
}