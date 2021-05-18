package com.wenitech.cashdaily.domain.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.data.model.*

import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    // Application
    fun getUserAppProfile(): Flow<ResourceAuth<User>>
    fun getUserAppBox(): Flow<Resource<Caja>>
    fun getRecentCashMovements(): Flow<Resource<List<MovimientoCaja>>>
    fun saveMoneyOnBox(money: Double): Flow<Resource<String>>
    fun removeMoneyOnBox(money: Double): Flow<Resource<String>>

    // Client
    fun getClients(): Flow<Resource<List<Cliente>>>
    fun getClientsCobrarHoy(): Flow<Resource<List<Cliente>>>
    fun getClientsAtrasados(): Flow<Resource<List<Cliente>>>
    fun getClientsVencidos(): Flow<Resource<List<Cliente>>>
    fun saveNewClient(cliente: Cliente): Flow<Resource<String>>
    fun updateClient(cliente: Cliente): Flow<Resource<String>>
    fun removeClient(clientId: String): Flow<Resource<String>>

    // CreditOfClient
    fun getCreditClientByDocumentReference(creditClientDocRef: String): Flow<Resource<Credito>>
    fun saveNewCreditByClientId(idClient: String, newCredit: Credito): MutableLiveData<ResourceAuth<String>>
    fun getQuotaCreditActive(docRefCreditActive: String): Flow<Resource<List<Cuota>>>
    fun saveQuotaOfCreditClient(cuota: Double, idClient: String, referenceCredit: String, user: FirebaseUser): Flow<Resource<String>>

    // Estadisticas
    fun getReports(): Flow<Resource<ReportsDaily>>
}