package com.wenitech.cashdaily.framework.features.authentication.recoverPasswordScreen

import android.text.TextUtils
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.usecases.auth.RecoverPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RecoverPasswordViewModel @Inject constructor(
    private val recoverPasswordUseCase: RecoverPasswordUseCase
) : ViewModel() {

    var uiState by mutableStateOf(RecoverPasswordUiState())
        private set

    var emailValue by mutableStateOf("")
        private set

    fun emailValueChange(emailValue: String) {
        this.emailValue = emailValue
        snapshotFlow { emailValue }
            .mapLatest { isEmailValid(it) }
            .onEach { setEnableButton(it) }
            .launchIn(viewModelScope)
    }

    fun dismissDialog() {
        uiState = uiState.copy(
            isLoadingSendEmail = false,
            isSuccessSendEmail = false,
            isErrorMessage = null
        )
    }

    fun sendEmailRecover(email: String) {
        viewModelScope.launch {
            recoverPasswordUseCase(email).collect { resultAuth ->
                when (resultAuth) {
                    is ResultAuth.Collision -> TODO()
                    is ResultAuth.Failed -> {
                        uiState = uiState.copy(
                            isLoadingSendEmail = false,
                            isSuccessSendEmail = false,
                            isErrorMessage = resultAuth.msg
                        )
                    }
                    ResultAuth.Loading -> {
                        uiState = uiState.copy(
                            isLoadingSendEmail = true,
                            isSuccessSendEmail = false,
                            isErrorMessage = null
                        )
                    }
                    is ResultAuth.Success -> {
                        emailValueChange("")
                        uiState = uiState.copy(
                            isLoadingSendEmail = false,
                            isSuccessSendEmail = true,
                            isErrorMessage = null
                        )
                    }
                }
            }
        }
    }

    private fun setEmailMessageError(message: String?) {
        uiState = uiState.copy(emailMessageError = message)
    }

    private fun setEnableButton(enabled: Boolean) {
        uiState = uiState.copy(isEnableButton = enabled)
    }

    private fun clearEditText() {
        emailValue = ""
    }

    private fun isEmailValid(email: String?): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                setEmailMessageError("Escribe tu correo")
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                setEmailMessageError("Esto no es un correo valido")
                false
            }
            else -> {
                setEmailMessageError(null)
                true
            }
        }
    }

}