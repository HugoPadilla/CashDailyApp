package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.domain.repositories.BoxRepository
import kotlinx.coroutines.flow.Flow

/**
 * Caso de uso: Obtiene las transacciones recientes hechas en la caja
 */
class GetRecentMovementsUseCase(
    private val boxRepository: BoxRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<CashTransactions>>> =
        boxRepository.getRecentMoves()
}