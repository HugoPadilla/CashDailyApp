package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.repositories.DataRepository
import com.wenitech.cashdaily.domain.entities.Quota
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveQuotaOfCreditClientUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        uid: String,
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<Resource<String>> = dataRepository.saveNewQuota(uid, idClient, idCredit, newQuota)
}
