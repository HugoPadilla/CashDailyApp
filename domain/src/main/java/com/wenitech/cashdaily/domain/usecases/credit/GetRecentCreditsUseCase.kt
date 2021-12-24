package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.repositories.CreditRepository

class GetRecentCreditsUseCase(
    private val creditRepository: CreditRepository
) {
    suspend operator fun invoke(uid: String, idClient: String) =
        creditRepository.getRecentCredits(uid, idClient)
}