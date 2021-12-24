package com.wenitech.cashdaily.data.repositories

import com.wenitech.cashdaily.data.entities.UserModel
import com.wenitech.cashdaily.data.entities.toUserDomain
import com.wenitech.cashdaily.data.remoteDataSource.UserRemoteDataSource
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun createDocumentNewUser(user: User): Boolean {
        return userRemoteDataSource.createDocumentUser(UserModel(email = user.email))
    }

    override suspend fun getUserProfile(): Flow<Response<User>> {
        return userRemoteDataSource.getUserProfile().transform {
            when (it) {
                is Response.Error -> return@transform emit(Response.Error(it.throwable, it.msg))
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.toUserDomain()))
            }
        }
    }
}