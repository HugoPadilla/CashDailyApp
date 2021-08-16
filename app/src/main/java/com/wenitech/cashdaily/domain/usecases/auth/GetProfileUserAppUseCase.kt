package com.wenitech.cashdaily.domain.usecases.auth

import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUserAppUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(uid: String): Flow<Resource<User>> {
        return dataRepository.getUserProfile(uid)
    }
}