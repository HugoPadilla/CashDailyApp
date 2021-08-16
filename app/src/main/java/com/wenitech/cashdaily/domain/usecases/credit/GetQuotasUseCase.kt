package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuotasUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<Resource<List<Quota>>> = dataRepository.getQuotaCredit(uid, idClient, idCredit)
}