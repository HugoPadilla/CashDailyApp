package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCreditClientUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<Resource<Credit>> =
        dataRepository.getCreditClient(uid, idClient, idCredit)

}