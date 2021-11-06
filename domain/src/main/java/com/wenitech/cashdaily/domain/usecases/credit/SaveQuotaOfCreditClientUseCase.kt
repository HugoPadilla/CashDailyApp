package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class SaveQuotaOfCreditClientUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        uid: String,
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<com.wenitech.cashdaily.domain.common.Resource<String>> =
        dataRepository.saveNewQuota(uid, idClient, idCredit, newQuota)
}
