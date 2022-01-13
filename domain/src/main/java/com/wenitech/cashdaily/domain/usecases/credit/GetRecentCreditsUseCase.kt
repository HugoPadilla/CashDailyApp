package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.repositories.CreditRepository
import kotlinx.coroutines.flow.Flow

class GetRecentCreditsUseCase(
    private val creditRepository: CreditRepository
) {
    suspend operator fun invoke(uid: String, idClient: String): Flow<Response<List<Credit>>> =
        creditRepository.getRecentCredits(uid, idClient)
}