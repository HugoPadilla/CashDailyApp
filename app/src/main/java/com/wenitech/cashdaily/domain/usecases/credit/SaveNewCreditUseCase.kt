package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.repositories.DataRepository
import com.wenitech.cashdaily.commons.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveNewCreditUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(
        uid: String,
        idClient: String,
        newCredit: Credit
    ): Flow<Resource<String>> = dataRepository.saveNewCredit(uid, idClient, newCredit)

}