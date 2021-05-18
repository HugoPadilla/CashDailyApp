package com.wenitech.cashdaily.features.client.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.Timestamp
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.model.Cliente
import com.wenitech.cashdaily.domain.interaction.client.SaveClientUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewClientViewModel @ViewModelInject constructor(
        private val saveClientUseCase: SaveClientUseCase,
) : ViewModel() {

    private val _resourceSaveClient: MutableLiveData<Resource<String>> = MutableLiveData()
    val resourceSaveClient: LiveData<Resource<String>> get() = _resourceSaveClient


    fun saveNewClient(fullName: String, cedulaClient: String, gender: String, phoneNumber: String, city: String, direction: String) {
        viewModelScope.launch {
            val cliente = Cliente(null, null, Timestamp.now(),fullName, cedulaClient, gender, phoneNumber, city, direction, false, null)
            saveClientUseCase.execute(cliente).collect {
                _resourceSaveClient.value = it
            }
        }

    }
}