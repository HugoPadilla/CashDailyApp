package com.wenitech.cashdaily.domain.interaction.credit

import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.model.Cuota
import com.wenitech.cashdaily.domain.repository.FirestoreRepository
import com.wenitech.cashdaily.core.ResourceAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuotasUseCase @Inject constructor(private val firestoreRepository: FirestoreRepository) {

    fun execute(docRefCreditActive: String): Flow<Resource<List<Cuota>>> {
        return firestoreRepository.getQuotaCreditActive(docRefCreditActive)
    }
}