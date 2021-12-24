package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.UserModel
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun createDocumentUser(userModel: UserModel): Boolean
    suspend fun getUserProfile(): Flow<Response<UserModel>>
}