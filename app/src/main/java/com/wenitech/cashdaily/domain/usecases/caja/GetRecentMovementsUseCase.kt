package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentMovementsUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(uid: String): Flow<Resource<List<CashTransactions>>> =
        dataRepository.getRecentMoves(uid)
}