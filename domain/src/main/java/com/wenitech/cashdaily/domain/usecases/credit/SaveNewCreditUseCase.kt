package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.repositories.DataRepository
import kotlinx.coroutines.flow.Flow

class SaveNewCreditUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        uid: String,
        idClient: String,
        newCredit: Credit
    ): Flow<com.wenitech.cashdaily.domain.common.Resource<String>> =
        dataRepository.saveNewCredit(uid, idClient, newCredit)

}