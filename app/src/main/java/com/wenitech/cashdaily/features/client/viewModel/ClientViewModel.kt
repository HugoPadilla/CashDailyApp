package com.wenitech.cashdaily.features.client.viewModel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.model.Cliente
import com.wenitech.cashdaily.domain.interaction.client.GetClientsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ClientViewModel @ViewModelInject constructor(
        private val getClientsUseCase: GetClientsUseCase,
) : ViewModel() {

    val _listClient = MutableLiveData<Resource<List<Cliente>>>()
    val listClients get() = _listClient

    fun getClient() {
        viewModelScope.launch {
            getClientsUseCase.execute().collect {
                _listClient.value = it
            }
        }
    }

}
