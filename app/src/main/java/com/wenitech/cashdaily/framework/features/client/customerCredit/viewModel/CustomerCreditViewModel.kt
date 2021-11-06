package com.wenitech.cashdaily.framework.features.client.customerCredit.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.domain.usecases.credit.GetCreditClientUseCase
import com.wenitech.cashdaily.domain.usecases.credit.GetQuotasUseCase
import com.wenitech.cashdaily.domain.usecases.credit.SaveQuotaOfCreditClientUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


class CustomerCreditViewModel @ViewModelInject constructor(
    private val auth: FirebaseAuth,
    private val getCreditClientUseCase: GetCreditClientUseCase,
    private val getQuotasUseCase: GetQuotasUseCase,
    private val saveQuotaOfCreditClientUseCase: SaveQuotaOfCreditClientUseCase,
) : ViewModel() {

    private val _idClient: MutableLiveData<String> = MutableLiveData()
    val idClient: LiveData<String> get() = _idClient

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _clientModel: MutableStateFlow<Client> = MutableStateFlow(Client())
    val clientModel: StateFlow<Client> = _clientModel

    private val _creditModel: MutableStateFlow<Credit> = MutableStateFlow(Credit())
    val customerState: StateFlow<Credit> = _creditModel

    private val _listQuotas: MutableStateFlow<List<Quota>> = MutableStateFlow(listOf())
    val quotaModelCustomer: StateFlow<List<Quota>> = _listQuotas

    private val _resultNewQuota: MutableLiveData<com.wenitech.cashdaily.domain.common.Resource<String>> =
        MutableLiveData()
    val resultNewQuota: LiveData<com.wenitech.cashdaily.domain.common.Resource<String>> get() = _resultNewQuota


    fun setArgs(idClient: String, refCredit: String) {
        getCreditClient(idClient = idClient, idCredit = refCredit)
    }

    fun setNewQuota(valueQuota: Double, idClient: String, refCreditActive: String) {
        viewModelScope.launch {

            val uid = auth.uid

            if (!uid.isNullOrEmpty() && idClient.isNotEmpty() && refCreditActive.isNotEmpty()) {

                val newQuota = Quota(
                    null,
                    Date(),
                    auth.currentUser!!.displayName!!,
                    valueQuota
                )

                saveQuotaOfCreditClientUseCase(uid, idClient, refCreditActive, newQuota).collect {
                    _resultNewQuota.value = it
                }
            } else {
                _resultNewQuota.value =
                    Resource.Failure(Throwable("No has iniciado sesion"))
            }
        }
    }

    private fun getCreditClient(idClient: String, idCredit: String) {
        viewModelScope.launch {

            val uid = auth.uid

            if (!uid.isNullOrEmpty() && idClient.isNotEmpty() && idCredit.isNotEmpty()) {
                getCreditClientUseCase(uid, idClient, idCredit).collect { creditResult ->
                    when (creditResult) {
                        is Resource.Failure -> {
                            _loading.value = false
                        }
                        is Resource.Loading -> {
                            _loading.value = true
                        }
                        is Resource.Success -> {
                            _loading.value = false
                            _creditModel.value = creditResult.data
                            getQuotasCreditClient(idClient = idClient, refCreditActive = idCredit)
                        }
                    }
                }
            }
        }
    }

    private fun getQuotasCreditClient(idClient: String, refCreditActive: String) {
        viewModelScope.launch {

            val uid = auth.uid

            if (!uid.isNullOrEmpty() && idClient.isNotEmpty() && refCreditActive.isNotEmpty()) {
                getQuotasUseCase(uid, idClient, refCreditActive).collect { result ->
                    when (result) {
                        is Resource.Failure -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {

                        }
                    }
                }
            }
        }
    }

}