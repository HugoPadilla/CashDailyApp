package com.wenitech.cashdaily.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.domain.repository.FirestoreRepository
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.data.firebase.Firestore
import com.wenitech.cashdaily.data.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreRepositoryImp @Inject constructor(
        private val firestore: Firestore,
) : FirestoreRepository {

    override fun getUserAppProfile(): Flow<ResourceAuth<User>> {
        return firestore.getProfileUser()
    }

    override fun getUserAppBox(): Flow<Resource<Caja>> {
        return firestore.getBoxUser()
    }

    override fun getRecentCashMovements(): Flow<Resource<List<MovimientoCaja>>> {
        return firestore.getRecentCashMovements()
    }

    override fun saveMoneyOnBox(money: Double): Flow<Resource<String>> {
        return firestore.saveMoneyOnBox(money)
    }

    override fun removeMoneyOnBox(money: Double): Flow<Resource<String>> {
        return firestore.removeMoneyOnBox(money)
    }

    override fun getClients(): Flow<Resource<List<Cliente>>> {
        return firestore.getClients()
    }

    override fun getClientsCobrarHoy(): Flow<Resource<List<Cliente>>> {
        return firestore.getClientsCobrarHoy()
    }

    override fun getClientsAtrasados(): Flow<Resource<List<Cliente>>> {
        TODO("Not yet implemented")
    }

    override fun getClientsVencidos(): Flow<Resource<List<Cliente>>> {
        TODO("Not yet implemented")
    }

    override fun saveNewClient(cliente: Cliente): Flow<Resource<String>> {
        return firestore.saveNewClient(cliente)
    }

    override fun updateClient(cliente: Cliente): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun removeClient(clientId: String): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun saveNewCreditByClientId(idClient: String, newCredit: Credito): MutableLiveData<ResourceAuth<String>> {
        return firestore.saveNewCreditClientById(idClient, newCredit)
    }

    override fun getCreditClientByDocumentReference(creditClientDocRef: String): Flow<Resource<Credito>> {
        return firestore.getCreditClient(creditClientDocRef)
    }

    override fun getQuotaCreditActive(docRefCreditActive: String): Flow<Resource<List<Cuota>>> {
        return firestore.getQuotaCredit(docRefCreditActive)
    }

    override fun saveQuotaOfCreditClient(cuota: Double, idClient: String, referenceCredit: String, user: FirebaseUser): Flow<Resource<String>> {
        return firestore.saveNewQuota(cuota, idClient, referenceCredit, user)
    }

    override fun getReports(): Flow<Resource<ReportsDaily>> {
        TODO("Not yet implemented")
    }
}