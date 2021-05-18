package com.wenitech.cashdaily.features.customerCredit.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseUser
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.domain.interaction.credit.GetCreditOfClientUseCase
import com.wenitech.cashdaily.domain.interaction.credit.GetQuotasUseCase
import com.wenitech.cashdaily.domain.interaction.credit.SaveQuotaOfCreditClientUseCase
import com.wenitech.cashdaily.features.customerCredit.model.ClientParcelable
import com.wenitech.cashdaily.data.model.Credito
import com.wenitech.cashdaily.data.model.Cuota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CustomerCreditViewModel @ViewModelInject constructor(
        private val getCreditOfClientUseCase: GetCreditOfClientUseCase,
        private val getQuotasUseCase: GetQuotasUseCase,
        private val saveQuotaOfCreditClientUseCase: SaveQuotaOfCreditClientUseCase,
) : ViewModel() {

    // Model Client Parcelable
    private var _clientParcelable = MutableLiveData<ClientParcelable>()
    val clientParcelable: LiveData<ClientParcelable> get() = _clientParcelable

    // Referencia al credito activo
    private var _docCreditActive: MutableLiveData<String> = MutableLiveData()
    val docCreditActive: LiveData<String> get() = _docCreditActive

    private val _creditClient = MutableLiveData<Resource<Credito>>()
    val creditClientLiveData get() = _creditClient

    private val _listQuotaCreditLiveData = MutableLiveData<Resource<List<Cuota>>>()
    val listQuotaCreditLiveData get() = _listQuotaCreditLiveData

    private var _resultNewQuota: MutableLiveData<Resource<String>> = MutableLiveData()
    val resultNewQuota: LiveData<Resource<String>> get() = _resultNewQuota




    fun changeArgument(clientParcelable: ClientParcelable) {
        _clientParcelable.value = clientParcelable
    }

    fun setDocRefCreditClient(referenceCredit: String?) {
        this._docCreditActive.value = referenceCredit
    }

    /**
     * Variable que guarda el credito activo
     * Es de un tipo liveData - Resource<T>, Un clase generica que encapsula los datos y el estado
     */
    fun getCreditClient(documentRef: String) {
        viewModelScope.launch {
            getCreditOfClientUseCase.execute(documentRef).collect {
                _creditClient.value = it
            }
        }
    }

    /**
     * Lista de quotas
     */
    fun getCuotasCreditClient(documentRef: String) {
        viewModelScope.launch {
            getQuotasUseCase.execute(documentRef).collect {
                _listQuotaCreditLiveData.value = it
            }
        }
    }


    fun setNewQuota(newQuota: Double?, idClient: String?, referenceCredit: String?, user: FirebaseUser?) {
        viewModelScope.launch {
            saveQuotaOfCreditClientUseCase.execute(newQuota!!, idClient!!, referenceCredit!!, user!!).collect {
                _resultNewQuota.value = it
            }
        }
    }
}