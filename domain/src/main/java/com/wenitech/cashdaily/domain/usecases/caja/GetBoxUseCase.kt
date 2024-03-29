package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.repositories.BoxRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso: Obtiene un Flow con la informacion de la caja
 */
class GetBoxUseCase @Inject constructor(
    private val boxRepository: BoxRepository
) {
    suspend operator fun invoke(): Flow<Response<Box>> =
        boxRepository.getUserBox()
}