package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.repositories.BoxRepository
import kotlinx.coroutines.flow.Flow

class RemoveMoneyOnBoxUseCase(
    private val boxRepository: BoxRepository
) {
    suspend operator fun invoke(
        money: Double,
        description: String
    ): Flow<Response<String>> =
        boxRepository.saveMoneyOnBox( money * -1, description)
}