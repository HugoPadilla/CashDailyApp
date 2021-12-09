package com.wenitech.cashdaily.framework.features.client.newClient

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.usecases.client.SaveClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RegisterClientViewModel @Inject constructor(
    private val saveClientUseCase: SaveClientUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterClientUiState())
    val uiState: StateFlow<RegisterClientUiState> get() = _uiState

    fun setFullName(fullName: String) {
        _uiState.value = _uiState.value.copy(fullName = fullName)
        isFormValid()
    }

    fun setIdNumber(fullName: String) {
        _uiState.value = _uiState.value.copy(idNumber = fullName)
        isFormValid()
    }

    fun setGender(fullName: String) {
        _uiState.value = _uiState.value.copy(gender = fullName)
        isFormValid()
    }

    fun setPhoneNumber(fullName: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = fullName)
        isFormValid()
    }

    fun setCity(fullName: String) {
        _uiState.value = _uiState.value.copy(city = fullName)
        isFormValid()
    }

    fun setDirection(fullName: String) {
        _uiState.value = _uiState.value.copy(direction = fullName)
        isFormValid()
    }

    fun onDismissDialog() {
        _uiState.value = _uiState.value.copy(showDialog = TypeDialog.None)
    }

    private fun clearForm() {
        _uiState.value = _uiState.value.copy(
            fullName = "",
            idNumber = "",
            gender = "",
            phoneNumber = "",
            city = "",
            direction = "",
            buttonEnabled = false
        )
    }

    fun saveNewClient() {
        if (isFormValid()) {
            val client = Client(
                paymentDate = Date(),
                finishDate = Date(),
                idNumber = _uiState.value.idNumber,
                fullName = _uiState.value.fullName,
                gender = _uiState.value.gender,
                phoneNumber = _uiState.value.phoneNumber,
                city = _uiState.value.city,
                direction = _uiState.value.direction,
                creditActive = false,
                refCredit = null
            )

            viewModelScope.launch {
                saveClientUseCase(client).collect {
                    when (it) {
                        is Resource.Failure -> {
                            _uiState.value = _uiState.value.copy(showDialog = TypeDialog.Error)
                        }
                        is Resource.Loading -> {
                            _uiState.value = _uiState.value.copy(showDialog = TypeDialog.Loading)
                        }
                        is Resource.Success -> {
                            _uiState.value = _uiState.value.copy(
                                showDialog = TypeDialog.Success,
                                registeredCustomerId = if (it.data.isEmpty()) it.data else "ffwefjf39f"
                            )
                            clearForm()
                            Log.d("resultregisterclient", "saveNewClient: ${it.data}")
                        }
                    }
                }
            }
        }

    }

    private fun isFormValid(): Boolean {

        val fullName = _uiState.value.fullName
        val idNumber = _uiState.value.idNumber
        val gender = _uiState.value.gender
        val phoneNumber = _uiState.value.phoneNumber
        val city = _uiState.value.city
        val direction = _uiState.value.direction

        _uiState.value = _uiState.value.copy(buttonEnabled = false)

        when {
            TextUtils.isEmpty(fullName) -> {
                return false
            }
            TextUtils.isEmpty(idNumber) -> {

                return false
            }
            TextUtils.isEmpty(gender) -> {

                return false
            }
            TextUtils.isEmpty(phoneNumber) -> {

                return false
            }
            TextUtils.isEmpty(city) -> {

                return false
            }
            TextUtils.isEmpty(direction) -> {

                return false
            }
            else -> {
                _uiState.value = _uiState.value.copy(buttonEnabled = true)
                return true
            }
        }

    }
}