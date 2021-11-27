package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class GetQuotasUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        idClient: String,
        idCredit: String
    ): Flow<com.wenitech.cashdaily.domain.common.Resource<List<Quota>>> =
        dataRepository.getQuotaCredit(idClient, idCredit)
}