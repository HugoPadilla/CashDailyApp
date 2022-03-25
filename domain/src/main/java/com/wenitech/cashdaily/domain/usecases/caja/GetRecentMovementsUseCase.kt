package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.domain.repositories.BoxRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Obtiene las transacciones recientes hechas en la caja
 */
class GetRecentMovementsUseCase @Inject constructor(
    private val boxRepository: BoxRepository
) {
    suspend operator fun invoke(): Flow<Response<List<CashTransactions>>> =
        boxRepository.getRecentMoves()
}