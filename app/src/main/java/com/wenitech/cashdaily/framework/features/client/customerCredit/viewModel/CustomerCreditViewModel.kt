package com.wenitech.cashdaily.framework.features.client.customerCredit.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.domain.usecases.client.GetClientById
import com.wenitech.cashdaily.domain.usecases.credit.GetCreditClientUseCase
import com.wenitech.cashdaily.domain.usecases.credit.GetQuotasUseCase
import com.wenitech.cashdaily.domain.usecases.credit.SaveQuotaOfCreditClientUseCase
import com.wenitech.cashdaily.framework.features.client.customerCredit.CustomerCreditState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerCreditViewModel @Inject constructor(
    private val getClientUseCase: GetClientById,
    private val getCreditClientUseCase: GetCreditClientUseCase,
    private val getQuotasUseCase: GetQuotasUseCase,
    private val saveQuotaOfCreditClientUseCase: SaveQuotaOfCreditClientUseCase,
) : ViewModel() {

    val TAG = "RESULT"

    private val _uiState = MutableStateFlow(CustomerCreditState())
    val uiState: StateFlow<CustomerCreditState> get() = _uiState

    private val _idClient: MutableStateFlow<String> = MutableStateFlow("")

    init {
    }

    fun setIdClient(idClient: String?, refCredit: String?) {
        _idClient.value = idClient ?: ""

        idClient?.let {
            getCurrentClient(it)
        }

        if (refCredit != null) {
            getCreditClient(idClient = idClient!!, idCredit = refCredit)
        }
    }

    /**
     * Inserta nueva cuota en el credito actual
     */
    fun setNewQuota(valueQuota: Double, idClient: String, refCreditActive: String) {
        viewModelScope.launch {

            saveQuotaOfCreditClientUseCase(
                idClient,
                refCreditActive,
                Quota(value = valueQuota)
            ).collect {
                when (it) {
                    is Response.Error -> {

                    }
                    is Response.Loading -> {

                    }
                    is Response.Success -> {

                    }
                }
            }

        }
    }

    private fun getCurrentClient(idClient: String){
        viewModelScope.launch {
            getClientUseCase(idClient = idClient).collect { clientResult ->
                when(clientResult){
                    is Response.Error -> {

                    }
                    is Response.Loading -> {

                    }
                    is Response.Success -> {
                        _uiState.value = _uiState.value.copy(
                            client = clientResult.data
                        )
                    }
                }
            }
        }
    }

    private fun getCreditClient(idClient: String, idCredit: String) {
        viewModelScope.launch {
            getCreditClientUseCase(idClient, idCredit).collect { creditResult ->
                when (creditResult) {
                    is Response.Error -> {
                        Log.d(TAG, "getCreditClient: Ocurrio un error")
                    }
                    is Response.Loading -> {
                        Log.d(TAG, "getCreditClient: Cargando informacion")
                    }
                    is Response.Success -> {
                        Log.d(TAG, "getCreditClient: ${creditResult.data}")
                        _uiState.value = _uiState.value.copy(credit = creditResult.data)
                        getQuotasCreditClient(idClient = idClient, refCreditActive = idCredit)
                    }
                }
            }
        }
    }

    private fun getQuotasCreditClient(idClient: String, refCreditActive: String) {
        viewModelScope.launch {

            getQuotasUseCase(idClient, refCreditActive).collect { result ->
                when (result) {
                    is Response.Error -> {
                        Log.d(
                            TAG,
                            "getQuotasCreditClient: Se presento un error ${result.throwable}"
                        )
                    }
                    is Response.Loading -> {
                        Log.d(TAG, "getQuotasCreditClient: Cargando cuotas")
                    }
                    is Response.Success -> {
                        _uiState.value = _uiState.value.copy(listQuota = result.data)
                    }
                }
            }
        }
    }
}
