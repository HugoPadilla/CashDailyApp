package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.repositories.BoxRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveMoneyOnBoxUseCase @Inject constructor(
    private val boxRepository: BoxRepository
) {
    suspend operator fun invoke(
        money: Double,
        description: String
    ): Flow<Response<String>> =
        boxRepository.saveMoneyOnBox(money * -1, description)
}