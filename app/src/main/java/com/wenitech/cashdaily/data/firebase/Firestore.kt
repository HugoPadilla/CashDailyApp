package com.wenitech.cashdaily.data.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.data.model.*
import kotlinx.coroutines.flow.Flow

interface Firestore {

    // Application
    fun getProfileUser(): Flow<ResourceAuth<User>>
    fun getBoxUser(): Flow<Resource<Caja>>
    fun getRecentCashMovements(): Flow<Resource<List<MovimientoCaja>>>
    fun saveMoneyOnBox(moneyToCash: Double): Flow<Resource<String>>
    fun removeMoneyOnBox(moneyToCash: Double): Flow<Resource<String>>

    // Client
    fun getClients(): Flow<Resource<List<Cliente>>>
    fun getClientsCobrarHoy(): Flow<Resource<List<Cliente>>>
    fun getClientsAtrasados(): Flow<Resource<List<Cliente>>>
    fun getClientsVencidos(): Flow<Resource<List<Cliente>>>
    fun saveNewClient(cliente: Cliente): Flow<Resource<String>>
    fun updateClient(cliente: Cliente): Flow<Resource<String>>
    fun removeClient(clientId: String): Flow<Resource<String>>

    // Credit
    fun getRecentCreditClient(idClient: String): Flow<Resource<List<Credito>>>
    fun getCreditClient(creditClientDocRef: String): Flow<Resource<Credito>>
    fun saveNewCreditClientById(idClient: String, newCredit: Credito): MutableLiveData<ResourceAuth<String>>
    fun getQuotaCredit(docRefCreditActive: String): Flow<Resource<List<Cuota>>>
    fun saveNewQuota(newQuota: Double, idClient: String, referenceCredit: String, user: FirebaseUser): Flow<Resource<String>>

    // Estadisticas
    fun getReports(): Flow<Resource<ReportsDaily>>
}