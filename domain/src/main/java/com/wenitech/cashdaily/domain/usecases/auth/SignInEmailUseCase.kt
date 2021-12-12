package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignInEmailUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Flow<ResultAuth<Boolean>> =
        authRepository.singInEmail(name, email, password)
}