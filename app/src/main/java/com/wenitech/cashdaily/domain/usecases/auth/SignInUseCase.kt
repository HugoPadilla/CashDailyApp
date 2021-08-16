package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.commons.ResultAuth
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Flow<ResultAuth<String>> =
        authRepository.singIn(name, email, password)
}