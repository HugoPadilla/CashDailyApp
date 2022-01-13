package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun addNewUser(user: User): Boolean
    suspend fun getUserProfile(): Flow<Response<User>>
    suspend fun deleteUserProfile(): Boolean
}