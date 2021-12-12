package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.repositories.AuthRepository

class SignOutUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.signOut()
}
