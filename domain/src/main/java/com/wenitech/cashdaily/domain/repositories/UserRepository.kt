package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createDocumentNewUser(user: User): Boolean
    suspend fun getUserProfile(): Flow<Resource<User>>
}