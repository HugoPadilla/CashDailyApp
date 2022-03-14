package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Boolean> = authRepository.getFirebaseAuthState()
}