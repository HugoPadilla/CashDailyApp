package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.repositories.CreditRepository
import kotlinx.coroutines.flow.Flow

class GetCreditClientUseCase(
    private val creditRepository: CreditRepository
) {
    suspend operator fun invoke(
        idClient: String,
        idCredit: String
    ): Flow<Response<Credit>> =
        creditRepository.getCreditClient(idClient, idCredit)

}