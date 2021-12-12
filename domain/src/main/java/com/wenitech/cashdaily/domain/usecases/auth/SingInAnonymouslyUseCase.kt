package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.repositories.AuthRepository

class SingInAnonymouslyUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.singInAnonymously()
}