package com.wenitech.cashdaily.domain.interaction.client

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.QuerySnapshot
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.model.Cliente
import com.wenitech.cashdaily.domain.repository.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClientsCobrarHoyUseCase @Inject constructor(
        private val firestoreRepository: FirestoreRepository,
) {

    fun execute(): Flow<Resource<List<Cliente>>> {
        return firestoreRepository.getClientsCobrarHoy()
    }

}