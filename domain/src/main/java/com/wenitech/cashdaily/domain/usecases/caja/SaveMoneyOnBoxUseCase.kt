package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class SaveMoneyOnBoxUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        uid: String,
        money: Double,
        description: String
    ): Flow<com.wenitech.cashdaily.domain.common.Resource<String>> =
        dataRepository.saveMoneyOnBox(money, description)
}