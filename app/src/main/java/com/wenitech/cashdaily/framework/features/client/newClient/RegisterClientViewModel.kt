package com.wenitech.cashdaily.framework.features.client.newClient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.usecases.client.SaveClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RegisterClientViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val saveClientUseCase: SaveClientUseCase,
) : ViewModel() {

    private val _resourceSaveClient: MutableLiveData<Resource<String>> =
        MutableLiveData()
    val resourceSaveClient: LiveData<Resource<String>> get() = _resourceSaveClient

    fun saveNewClient(
        fullName: String,
        cedulaClient: String,
        gender: String,
        phoneNumber: String,
        city: String,
        direction: String
    ) {

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

        viewModelScope.launch {
            saveClientUseCase(client).collect {
                _resourceSaveClient.value = it
            }
        }

    }
}