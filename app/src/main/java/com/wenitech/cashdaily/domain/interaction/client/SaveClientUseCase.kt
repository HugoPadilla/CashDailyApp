package com.wenitech.cashdaily.domain.interaction.client

import androidx.lifecycle.MutableLiveData
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.data.model.Cliente
import com.wenitech.cashdaily.domain.repository.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveClientUseCase @Inject constructor(private val firestoreRepository: FirestoreRepository) {

    fun execute(cliente: Cliente): Flow<Resource<String>> {
        return firestoreRepository.saveNewClient(cliente)
    }
}