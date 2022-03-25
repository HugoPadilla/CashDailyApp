package com.wenitech.cashdaily.data.repositories

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.repositories.LoginRepository
import com.wenitech.cashdaily.domain.repositories.RegistrationRepository
import com.wenitech.cashdaily.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val loginRepository: LoginRepository,
    private val registrationRepository: RegistrationRepository,
) : UserRepository {
    override  fun getUserProfile(): Flow<Response<User>> {
        return loginRepository.getUserProfile()
    }

    override fun isUserAuthenticated(): Boolean {
       return loginRepository.isUserAuthenticated()
    }

    override fun authState(): Flow<Boolean> {
        return loginRepository.authState()
    }

    override fun loginWithEmail(email: String, password: String): Flow<ResultAuth<Boolean>> {
        return loginRepository.loginWithEmail(email, password)
    }

    override fun signOut(): Flow<ResultAuth<Boolean>> {
        return loginRepository.signOut()
    }

    override fun registerWithEmail(email: String, password: String): Flow<ResultAuth<String>> {
        return registrationRepository.registerWithEmail(email, password)
    }

    override fun registerWithAnonymously(): Flow<ResultAuth<Boolean>> {
        return registrationRepository.registerWithAnonymously()
    }

    override fun sendRecoveryPasswordMessageToEmail(email: String): Flow<ResultAuth<Boolean>> {
        return registrationRepository.sendRecoveryPasswordMessageToEmail(email)
    }
}