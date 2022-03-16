package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.repositories.UserRepository
import javax.inject.Inject

class IsUserAuthenticatedUseCase @Inject constructor(
    private val loginRepository: UserRepository
) {
    operator fun invoke(): Boolean = loginRepository.isUserAuthenticated()
}