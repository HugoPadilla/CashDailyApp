package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.ResultAuth
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean
    suspend fun loginWithEmail(email: String, password: String): Flow<ResultAuth<Boolean>>
    fun signOut(): Flow<ResultAuth<Boolean>>
    suspend fun singInEmail(
        name: String,
        email: String,
        password: String
    ): Flow<ResultAuth<Boolean>>

    suspend fun singInAnonymously(): Flow<ResultAuth<Boolean>>
    suspend fun sendRecoverPassword(email: String): Flow<ResultAuth<Boolean>>
    fun getFirebaseAuthState(): Flow<Boolean>
}