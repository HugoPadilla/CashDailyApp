package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignOutUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<ResultAuth<Boolean>> = authRepository.signOut()
}
