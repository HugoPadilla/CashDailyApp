package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class GetRecentMovementsUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<CashTransactions>>> =
        dataRepository.getRecentMoves()
}