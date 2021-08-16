package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.commons.ResultAuth
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<ResultAuth<String>> =
        authRepository.login(email, password)
}