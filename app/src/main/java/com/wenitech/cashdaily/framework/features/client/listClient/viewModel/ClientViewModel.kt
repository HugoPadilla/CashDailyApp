package com.wenitech.cashdaily.framework.features.client.listClient.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.usecases.client.GetAllClientsPagingUseCase
import com.wenitech.cashdaily.framework.features.client.listClient.ClientContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ClientViewModel @ViewModelInject constructor(
    private val auth: FirebaseAuth,
    private val getAllClientsPagingUseCase: GetAllClientsPagingUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData<ClientContract.ClientState>()
    val uiState: LiveData<ClientContract.ClientState> = _uiState

    private val _listClient = MutableStateFlow(listOf<Client>())
    val listClient: StateFlow<List<Client>> = _listClient

    init {
        fetchClients()
    }

    private fun fetchClients() {
        viewModelScope.launch {
            val uid = auth.uid
            if (!uid.isNullOrEmpty()){
                getAllClientsPagingUseCase().collect {
                    when (it) {
                        is Resource.Failure -> _uiState.value = ClientContract.ClientState.Error
                        is Resource.Loading -> _uiState.value = ClientContract.ClientState.Loading
                        is Resource.Success -> {
                            _uiState.value = ClientContract.ClientState.Success(it.data)
                            _listClient.value = it.data
                        }
                    }
                }
            } else {
                _uiState.value = ClientContract.ClientState.Success(listOf())
            }
        }
    }


}
