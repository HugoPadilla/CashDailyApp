package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class GetCreditClientUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<com.wenitech.cashdaily.domain.common.Resource<Credit>> =
        dataRepository.getCreditClient(uid, idClient, idCredit)

}