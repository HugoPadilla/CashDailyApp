package com.wenitech.cashdaily.framework.features.client.listClient.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.usecases.client.GetAllClientsPagingUseCase
import com.wenitech.cashdaily.framework.features.client.listClient.ClientState
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

    private val _uiState = MutableStateFlow(ClientState())
    val uiStates: StateFlow<ClientState> = _uiState

    init {
        fetchClients()
    }

    private fun fetchClients() {
        viewModelScope.launch {
            getAllClientsPagingUseCase().collect {
                when (it) {
                    is Resource.Failure -> {
                        _uiState.value = _uiState.value.copy(loading = false, errorMessage = it.msg)
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(loading = true, errorMessage = null)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            listClient = it.data,
                            loading = false,
                            errorMessage = null
                        )

                    }
                }
            }
        }
    }

}
