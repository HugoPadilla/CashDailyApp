package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class SaveQuotaOfCreditClientUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<Resource<String>> =
        dataRepository.saveNewQuota(idClient, idCredit, newQuota)
}
