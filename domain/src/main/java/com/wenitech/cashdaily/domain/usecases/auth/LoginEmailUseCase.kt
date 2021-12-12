package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class LoginEmailUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<ResultAuth<Boolean>> =
        authRepository.loginWithEmail(email, password)
}