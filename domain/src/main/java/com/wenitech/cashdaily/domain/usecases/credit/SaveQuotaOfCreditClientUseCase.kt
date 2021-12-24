package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.domain.repositories.CreditRepository
import kotlinx.coroutines.flow.Flow

class SaveQuotaOfCreditClientUseCase(
    private val creditRepository: CreditRepository
) {
    suspend operator fun invoke(
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<Response<String>> =
        creditRepository.saveNewQuota(idClient, idCredit, newQuota)
}
