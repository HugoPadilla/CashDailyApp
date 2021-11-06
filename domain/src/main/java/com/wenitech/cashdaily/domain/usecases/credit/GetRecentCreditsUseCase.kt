package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.repositories.DataRepository

class GetRecentCreditsUseCase(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(uid: String, idClient: String) =
        dataRepository.getRecentCredits(uid, idClient)
}