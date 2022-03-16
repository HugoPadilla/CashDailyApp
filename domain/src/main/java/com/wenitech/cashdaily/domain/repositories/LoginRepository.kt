package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun isUserAuthenticated(): Boolean

    fun authState(): Flow<Boolean>

    fun getUserProfile(): Flow<Response<User>>

    fun loginWithEmail(email: String, password: String): Flow<ResultAuth<Boolean>>

    fun signOut(): Flow<ResultAuth<Boolean>>
}