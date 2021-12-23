package com.wenitech.cashdaily.framework.features.authentication.loginScreen.viewModel

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Status.*
import com.wenitech.cashdaily.domain.usecases.auth.LoginEmailUseCase
import com.wenitech.cashdaily.framework.features.authentication.loginScreen.state.LoginUiState
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
class LoginViewModel @Inject constructor(
    private val loginEmailUseCase: LoginEmailUseCase
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    init {
        snapshotFlow { email.value to password.value }
            .mapLatest {
                isEmailValid(it.first) && isPasswordValid(it.second)
            }.onEach {
                setEnableButton(it)
            }
            .launchIn(viewModelScope)
    }

    fun setEmail(email: String) {
        this.email.value = email
    }

    fun setPassword(password: String) {
        this.password.value = password
    }

    fun onDismissLoadingDialog() {
        uiState = uiState.copy(isErrorLogin = false)
    }

    fun doLogIn(email: String, password: String) {
        if (isEmailValid(email) && isPasswordValid(password)) {
            viewModelScope.launch {
                try {
                    loginEmailUseCase(email, password).collect {
                        when (it.status) {
                            LOADING -> {
                                uiState = uiState.copy(
                                    isSuccessLogin = false,
                                    isErrorLogin = false,
                                    isLoadingLogin = true,
                                )
                            }
                            SUCCESS -> {
                                uiState = uiState.copy(
                                    isSuccessLogin = true,
                                    isErrorLogin = false,
                                    isLoadingLogin = false,
                                )
                            }
                            COLLICION -> {
                                // Not used
                            }
                            FAILED -> {
                                uiState = uiState.copy(
                                    isSuccessLogin = false,
                                    isErrorLogin = true,
                                    isLoadingLogin = false,
                                )
                            }
                        }
                    }
                } catch (e: Throwable) {
                    Log.d("TAG", "logIn: ${e.message.toString()}")
                }
            }
        }
    }

    private fun setEmailMessageError(msg: String?) {
        uiState = uiState.copy(emailMessageError = msg)
    }

    private fun setPasswordMessageError(msg: String?) {
        uiState = uiState.copy(passwordMessageError = msg)
    }

    private fun setEnableButton(enableButton: Boolean) {
        uiState = uiState.copy(isEnableButton = enableButton)
    }

    private fun isEmailValid(email: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                setEmailMessageError("Escribe tu correo")
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                setEmailMessageError("Escribe un correo valido")
                false
            }
            else -> {
                setEmailMessageError(null)
                true
            }
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return when {
            TextUtils.isEmpty(password) -> {
                setPasswordMessageError("Escribe tu contraseña")
                false
            }
            password.length < 8 -> {
                setPasswordMessageError("Debe tener al menos 8 carapteres")
                false
            }
            else -> {
                setPasswordMessageError(null)
                true
            }

        }
    }

}