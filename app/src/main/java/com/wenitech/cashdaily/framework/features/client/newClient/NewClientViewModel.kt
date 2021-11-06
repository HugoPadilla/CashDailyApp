package com.wenitech.cashdaily.framework.features.client.newClient

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.usecases.client.SaveClientUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class NewClientViewModel @ViewModelInject constructor(
    private val auth: FirebaseAuth,
    private val saveClientUseCase: SaveClientUseCase,
) : ViewModel() {

    private val _resourceSaveClient: MutableLiveData<com.wenitech.cashdaily.domain.common.Resource<String>> =
        MutableLiveData()
    val resourceSaveClient: LiveData<com.wenitech.cashdaily.domain.common.Resource<String>> get() = _resourceSaveClient

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
            paymentDate = Date(),
            finishDate = Date(),
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