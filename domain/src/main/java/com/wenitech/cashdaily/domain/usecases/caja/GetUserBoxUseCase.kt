package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class GetUserBoxUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(uid: String): Flow<Resource<Box>> =
        dataRepository.getUserBox()
}