package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        email: String,
        password: String
    ): Flow<ResultAuth<String>> {
        return userRepository.registerWithEmail(email, password)
    }
}