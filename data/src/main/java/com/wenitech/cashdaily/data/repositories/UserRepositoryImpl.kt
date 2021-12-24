package com.wenitech.cashdaily.data.repositories

import com.wenitech.cashdaily.data.entities.UserModel
import com.wenitech.cashdaily.data.entities.toUserDomain
import com.wenitech.cashdaily.data.remoteDataSource.UserRemoteDataSource
import com.wenitech.cashdaily.domain.common.Resource
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

    override suspend fun getUserProfile(): Flow<Resource<User>> {
        return userRemoteDataSource.getUserProfile().transform {
            when (it) {
                is Resource.Failure -> return@transform emit(Resource.Failure(it.throwable, it.msg))
                is Resource.Loading -> return@transform emit(Resource.Loading())
                is Resource.Success -> return@transform emit(Resource.Success(it.data.toUserDomain()))
            }
        }
    }
}