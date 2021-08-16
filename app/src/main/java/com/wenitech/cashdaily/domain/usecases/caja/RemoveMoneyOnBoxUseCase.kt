package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveMoneyOnBoxUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        uid: String,
        money: Double,
        description: String
    ): Flow<Resource<String>> = dataRepository.saveMoneyOnBox(uid, money * -1, description)
}