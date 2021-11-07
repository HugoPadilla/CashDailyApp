package com.wenitech.cashdaily.framework.features.client.listClient.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.usecases.client.GetAllClientsPagingUseCase
import com.wenitech.cashdaily.framework.features.client.listClient.ClientContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val getAllClientsPagingUseCase: GetAllClientsPagingUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData<ClientContract.ClientState>()
    val uiState: LiveData<ClientContract.ClientState> = _uiState

    private val _listClient = MutableStateFlow(listOf<Client>())
    val listClientModel: StateFlow<List<Client>> = _listClient

    init {
        fetchClients()
    }

    private fun fetchClients() {
        viewModelScope.launch {
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

        }
    }

}
