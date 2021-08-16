package com.wenitech.cashdaily.framework.features.client.customerCredit.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.usecases.credit.GetCreditClientUseCase
import com.wenitech.cashdaily.domain.usecases.credit.GetQuotasUseCase
import com.wenitech.cashdaily.domain.usecases.credit.SaveQuotaOfCreditClientUseCase
import com.wenitech.cashdaily.framework.features.client.customerCredit.BaseMviViewModel
import com.wenitech.cashdaily.framework.features.client.customerCredit.CustomerContract
import com.wenitech.cashdaily.framework.features.client.customerCredit.ReduceAction
import com.wenitech.cashdaily.framework.features.client.customerCredit.model.ClientParcelable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

sealed class CustomerReduceAction : ReduceAction {
    object Loading : CustomerReduceAction()
    data class Loaded(val credit: Credit) : CustomerReduceAction()
    data class LoadError(val message: String) : CustomerReduceAction()
}

class CustomerCreditViewModel @ViewModelInject constructor(
    private val auth: FirebaseAuth,
    private val getCreditClientUseCase: GetCreditClientUseCase,
    private val getQuotasUseCase: GetQuotasUseCase,
    private val saveQuotaOfCreditClientUseCase: SaveQuotaOfCreditClientUseCase,
) : BaseMviViewModel<CustomerContract.CustomerState, CustomerContract.CustomerEvent, CustomerReduceAction>(
    initialState = CustomerContract.CustomerState.initial
) {

    // Model Client Parcelable
    private val clientParzelableMutableLiveData = MutableLiveData<ClientParcelable>()
    val clientParcelable: LiveData<ClientParcelable> get() = clientParzelableMutableLiveData

    private val _resultNewQuota: MutableLiveData<Resource<String>> = MutableLiveData()
    val resultNewQuota: LiveData<Resource<String>> get() = _resultNewQuota

    private val _customerState: MutableLiveData<CustomerContract.CustomerState> = MutableLiveData()
    val customerState: LiveData<CustomerContract.CustomerState> = _customerState

    private val _quotaCustomer: MutableLiveData<CustomerContract.QuotaCustomerState> =
        MutableLiveData()
    val quotaCustomer: LiveData<CustomerContract.QuotaCustomerState> = _quotaCustomer


    fun process(event: CustomerContract.CustomerEvent) {
        when (event) {
            is CustomerContract.CustomerEvent.SetNewQuota -> setNewQuota(event.value)
            is CustomerContract.CustomerEvent.SetClientParzelable -> setClientParcelable(event.clientParcelable)
            is CustomerContract.CustomerEvent.getCustomerCredit -> getCreditClient(
                event.idClient,
                event.idCredit
            )
        }
    }

    /**
     * Carga el objeto cliente enviado como argumento desde Navigation
     *
     * @param clientParcelable - Objeto parcelable que se envia como
     * parametro personalizado en la navegacion
     */
    private fun setClientParcelable(clientParcelable: ClientParcelable) {
        clientParzelableMutableLiveData.value = clientParcelable
    }

    private fun getCreditClient(idClient: String, idCredit: String) {
        viewModelScope.launch {

            val uid = auth.uid

            if (!uid.isNullOrEmpty() && !idClient.isNullOrEmpty() && !idCredit.isNullOrEmpty()) {
                getCreditClientUseCase(uid, idClient, idCredit).collect { creditResult ->
                    when (creditResult) {
                        is Resource.Failure -> {
                            /* _customerState.value =
                                 CustomerContract.CustomerState.Error(creditResult.throwable)*/
                        }
                        is Resource.Loading -> {
                            // _customerState.value = CustomerContract.CustomerState.Loading
                        }
                        is Resource.Success -> {
                            /* _customerState.value = CustomerContract.CustomerState.Success(
                                 creditResult.data
                             )*/
                            getQuotasCreditClient()
                        }
                    }
                }
            } else {
                //  _customerState.value = CustomerContract.CustomerState.Error(Throwable())
            }
        }
    }

    private fun getQuotasCreditClient() {
        viewModelScope.launch {

            val uid = auth.uid
            val idClient = clientParcelable.value?.id
            val refCreditActive = clientParcelable.value?.idCredit

            if (!uid.isNullOrEmpty() && !idClient.isNullOrEmpty() && !refCreditActive.isNullOrEmpty()) {
                getQuotasUseCase(uid, idClient, refCreditActive).collect {
                    when (it) {
                        is Resource.Failure -> {
                            _quotaCustomer.value = CustomerContract.QuotaCustomerState.Error
                        }
                        is Resource.Loading -> {
                            _quotaCustomer.value = CustomerContract.QuotaCustomerState.Loading
                        }
                        is Resource.Success -> {
                            if (it.data.isEmpty()) {
                                _quotaCustomer.value = CustomerContract.QuotaCustomerState.Empty
                            } else {
                                _quotaCustomer.value =
                                    CustomerContract.QuotaCustomerState.Success(it.data)
                            }
                        }
                    }
                }
            } else {
                _quotaCustomer.value = CustomerContract.QuotaCustomerState.Empty
            }
        }
    }

    private fun setNewQuota(valueQuota: Double) {
        viewModelScope.launch {

            val uid = auth.uid
            val idClient = clientParcelable.value?.id
            val refCreditActive = clientParcelable.value?.idCredit

            if (!uid.isNullOrEmpty() && !idClient.isNullOrEmpty() && !refCreditActive.isNullOrEmpty()) {

                val newQuota = com.wenitech.cashdaily.domain.entities.Quota(
                    null,
                    null,
                    auth.currentUser!!.displayName!!,
                    valueQuota
                )

                saveQuotaOfCreditClientUseCase(uid, idClient, refCreditActive, newQuota).collect {
                    _resultNewQuota.value = it
                }
            } else {
                _resultNewQuota.value = Resource.Failure(Throwable("No has iniciado sesion"))
            }
        }
    }

    override suspend fun executeIntent(mviIntent: CustomerContract.CustomerEvent) {
        when (mviIntent) {
            is CustomerContract.CustomerEvent.SetClientParzelable -> TODO()
            is CustomerContract.CustomerEvent.SetNewQuota -> TODO()
            is CustomerContract.CustomerEvent.getCustomerCredit -> {
                //handle(CustomerReduceAction.Loading)
                val uid = auth.uid

                if (!uid.isNullOrEmpty()) {
                    getCreditClientUseCase(
                        uid,
                        mviIntent.idClient,
                        mviIntent.idCredit
                    ).collect { creditResult ->
                        when (creditResult) {
                            is Resource.Failure -> {
                                handle(CustomerReduceAction.LoadError("Presentamos incovenientes, intente mas tarde"))
                            }
                            is Resource.Loading -> {
                               handle(CustomerReduceAction.Loading)
                            }
                            is Resource.Success -> {
                                handle(CustomerReduceAction.Loaded(credit = creditResult.data))
                                getQuotasCreditClient()
                            }
                        }
                    }
                } else {
                    handle(CustomerReduceAction.LoadError("No se ha iniciado sesion. Es necesario autenticarse"))
                }
            }
        }
    }

    override fun reduce(
        state: CustomerContract.CustomerState,
        reduceAction: CustomerReduceAction
    ): CustomerContract.CustomerState = when (reduceAction) {
        is CustomerReduceAction.LoadError -> state.copy(
            loadState = CustomerContract.LoadState.ERROR,
            errorMessage = reduceAction.message
        )
        is CustomerReduceAction.Loaded -> state.copy(
            loadState = CustomerContract.LoadState.LOADED,
            credit = reduceAction.credit,
            errorMessage = ""
        )
        CustomerReduceAction.Loading -> state.copy(
            loadState = CustomerContract.LoadState.LOADING,
            errorMessage = ""
        )
    }
}