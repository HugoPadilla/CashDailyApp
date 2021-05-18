package com.wenitech.cashdaily.domain.interaction.caja

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.firebase.model.CajaLiveData
import com.wenitech.cashdaily.domain.repository.FirestoreRepository
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.data.model.Caja
import com.wenitech.cashdaily.data.model.MovimientoCaja
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserAppBoxUseCase @Inject constructor(private val firestoreRepository: FirestoreRepository) {

    fun execute(): Flow<Resource<Caja>> {
        return firestoreRepository.getUserAppBox()
    }

    fun getRecentCashMovements(): Flow<Resource<List<MovimientoCaja>>> {
        return firestoreRepository.getRecentCashMovements()
    }

    fun saveMoneyOnBox(money: Double): Flow<Resource<String>> {
        return firestoreRepository.saveMoneyOnBox(money)
    }

    fun removeMoneyOnBox(money: Double): Flow<Resource<String>> {
        return firestoreRepository.removeMoneyOnBox(money)
    }

}