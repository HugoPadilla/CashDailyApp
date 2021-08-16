package com.wenitech.cashdaily.framework.features.client.newClient

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.usecases.client.SaveClientUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewClientViewModel @ViewModelInject constructor(
    private val auth: FirebaseAuth,
    private val saveClientUseCase: SaveClientUseCase,
) : ViewModel() {

    private val _resourceSaveClient: MutableLiveData<Resource<String>> = MutableLiveData()
    val resourceSaveClient: LiveData<Resource<String>> get() = _resourceSaveClient

    fun saveNewClient(
        fullName: String,
        cedulaClient: String,
        gender: String,
        phoneNumber: String,
        city: String,
        direction: String
    ) {

        val uid = auth.currentUser?.uid
        val client = Client(
            id = null,
            creationDate = null,
            paymentDate = Timestamp.now(),
            finishDate = Timestamp.now(),
            idNumber = cedulaClient,
            fullName = fullName,
            gender = gender,
            phoneNumber = phoneNumber,
            city = city,
            direction = direction,
            creditActive = false,
            refCredit = null
        )

        if (!uid.isNullOrEmpty()) {
            viewModelScope.launch {
                saveClientUseCase(client).collect {
                    _resourceSaveClient.value = it
                }
            }
        } else {
            // User no login
        }

    }
}