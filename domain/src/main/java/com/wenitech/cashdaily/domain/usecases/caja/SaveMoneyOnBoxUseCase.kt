package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.repositories.BoxRepository
import kotlinx.coroutines.flow.Flow

class SaveMoneyOnBoxUseCase(
    private val dataRepository: BoxRepository
) {
    suspend operator fun invoke(
        money: Double,
        description: String
    ): Flow<Resource<String>> =
        dataRepository.saveMoneyOnBox(money, description)
}