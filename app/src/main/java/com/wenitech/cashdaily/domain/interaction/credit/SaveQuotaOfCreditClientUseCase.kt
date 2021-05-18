package com.wenitech.cashdaily.domain.interaction.credit

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.domain.repository.FirestoreRepository
import com.wenitech.cashdaily.core.ResourceAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveQuotaOfCreditClientUseCase @Inject constructor(private val firestoreRepository: FirestoreRepository) {

    fun execute(cuota: Double, referenceClient: String, referenceCredit: String, user: FirebaseUser): Flow<Resource<String>> {
        return firestoreRepository.saveQuotaOfCreditClient(cuota, referenceClient, referenceCredit, user)
    }
}