package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.commons.ResultAuth
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun getUisUser(): String?
    suspend fun login(email: String, password: String): Flow<ResultAuth<String>>
    suspend fun singIn(name: String, email: String, password: String): Flow<ResultAuth<String>>
    suspend fun sendRecoverPassword(email: String): Flow<ResultAuth<String>>
}