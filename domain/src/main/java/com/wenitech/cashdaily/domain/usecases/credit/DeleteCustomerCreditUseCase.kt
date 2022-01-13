package com.wenitech.cashdaily.domain.usecases.credit

import com.wenitech.cashdaily.domain.repositories.CreditRepository

class DeleteCustomerCreditUseCase(
    private val creditRepository: CreditRepository
) {
    suspend operator fun invoke(idClient: String, idCredit: String) =
        creditRepository.deleteCustomerCredit(idClient, idCredit)
}