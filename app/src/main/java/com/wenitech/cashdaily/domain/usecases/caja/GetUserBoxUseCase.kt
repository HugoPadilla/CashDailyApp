package com.wenitech.cashdaily.domain.usecases.caja

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.repositories.DataRepository
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.entities.CashTransactions
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserBoxUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(uid: String): Flow<Resource<Box>> = dataRepository.getUserBox(uid)
}