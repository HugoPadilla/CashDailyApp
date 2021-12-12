package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.repositories.AuthRepository

class GetFirebaseAuthState(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.getFirebaseAuthState()
}