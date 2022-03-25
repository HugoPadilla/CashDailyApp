package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.ResultAuth
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    fun registerWithEmail(email: String, password: String): Flow<ResultAuth<String>>

    fun registerWithAnonymously(): Flow<ResultAuth<Boolean>>

    fun sendRecoveryPasswordMessageToEmail(email: String): Flow<ResultAuth<Boolean>>
}