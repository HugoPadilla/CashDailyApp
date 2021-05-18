package com.wenitech.cashdaily.domain.interaction.credit

import androidx.lifecycle.MutableLiveData
import com.wenitech.cashdaily.data.model.Credito
import com.wenitech.cashdaily.domain.repository.FirestoreRepository
import com.wenitech.cashdaily.core.ResourceAuth
import javax.inject.Inject

class SaveCreditOfClientUseCase @Inject constructor(private val firestoreRepository: FirestoreRepository) {

    fun execute(idClient: String, newCredit: Credito): MutableLiveData<ResourceAuth<String>> {
        return firestoreRepository.saveNewCreditByClientId(idClient, newCredit)
    }

}