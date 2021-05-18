package com.wenitech.cashdaily.domain.interaction.credit

import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.model.Credito
import com.wenitech.cashdaily.domain.repository.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCreditOfClientUseCase @Inject constructor(private val firestoreRepository: FirestoreRepository) {

    fun execute(creditClientDocRef: String): Flow<Resource<Credito>> {
        return firestoreRepository.getCreditClientByDocumentReference(creditClientDocRef)
    }
}