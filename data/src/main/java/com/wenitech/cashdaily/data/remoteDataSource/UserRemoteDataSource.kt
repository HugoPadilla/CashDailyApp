package com.wenitech.cashdaily.data.remoteDataSource

import com.wenitech.cashdaily.data.entities.UserModel
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun createUserProfile(userModel: UserModel): Boolean

    fun readUserProfile(): Flow<Response<UserModel>>
}