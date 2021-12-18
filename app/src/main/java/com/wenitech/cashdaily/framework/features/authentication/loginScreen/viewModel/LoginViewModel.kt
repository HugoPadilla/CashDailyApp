package com.wenitech.cashdaily.framework.features.authentication.loginScreen.viewModel

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Status.*
import com.wenitech.cashdaily.domain.usecases.auth.LoginEmailUseCase
import com.wenitech.cashdaily.framework.features.authentication.loginScreen.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginEmailUseCase: LoginEmailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

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
        _state.value = _state.value.copy(shoDialogError = false)
    }

    fun doLogIn(email: String, password: String) {
        if (isEmailValid(email) && isPasswordValid(password)) {
            viewModelScope.launch {
                try {
                    loginEmailUseCase(email, password).collect {
                        when (it.status) {
                            LOADING -> {
                                _state.value = _state.value.copy(
                                    onSuccess = false,
                                    shoDialogError = false,
                                    shoDialogLoading = true,
                                )
                            }
                            SUCCESS -> {
                                _state.value = _state.value.copy(
                                    onSuccess = true,
                                    shoDialogError = false,
                                    shoDialogLoading = false,
                                )
                            }
                            COLLICION -> {
                                // Not used
                            }
                            FAILED -> {
                                _state.value = _state.value.copy(
                                    onSuccess = false,
                                    shoDialogError = true,
                                    shoDialogLoading = false,
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
        _state.value = _state.value.copy(emailMessageError = msg)
    }

    private fun setPasswordMessageError(msg: String?) {
        _state.value = _state.value.copy(passwordMessageError = msg)
    }

    private fun setEnableButton(enableButton: Boolean) {
        _state.value = _state.value.copy(buttonEnable = enableButton)
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