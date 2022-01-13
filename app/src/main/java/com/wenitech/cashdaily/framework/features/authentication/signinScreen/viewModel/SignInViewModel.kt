package com.wenitech.cashdaily.framework.features.authentication.signinScreen.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Status
import com.wenitech.cashdaily.domain.usecases.auth.SignInEmailUseCase
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.state.ResultEnum
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.state.SignInUiState
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
class SignInViewModel @Inject constructor(
    private val signInEmailUseCase: SignInEmailUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SignInUiState())
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordConfirm by mutableStateOf("")
        private set

    init {
        snapshotFlow {
            email to password to passwordConfirm
        }
            .mapLatest {
                isEmailValid(it.first.first)
                        && isPasswordValid(it.first.second)
                        && isConfirmPasswordValid(it.first.second, it.second)
            }.onEach {
                setEnableButton(it)
            }.launchIn(viewModelScope)
    }

    fun onEmailChange(email: String) {
        this.email = email
    }

    fun onPasswordChange(password: String) {
        this.password = password
    }

    fun onPasswordConfirmChange(passwordConfirm: String) {
        this.passwordConfirm = passwordConfirm
    }

    fun onDismissDialog() {
        uiState = uiState.copy(result = ResultEnum.Init)
    }

    private fun setEnableButton(enable: Boolean) {
        uiState = uiState.copy(buttonEnable = enable)
    }

    fun doSignIn(email: String, password: String) {
        viewModelScope.launch {
            signInEmailUseCase(email, password).collect { result ->
                uiState = when (result.status) {
                    Status.LOADING -> uiState.copy(result = ResultEnum.Loading)
                    Status.SUCCESS -> {
                        uiState.copy(result = ResultEnum.Success)
                    }
                    Status.COLLICION -> uiState.copy(result = ResultEnum.Collision)
                    Status.FAILED -> uiState.copy(result = ResultEnum.Failed)
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                uiState = uiState.copy(emailMessageError = "Escribe un correo")
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                uiState =
                    uiState.copy(emailMessageError = "Este no es un correo valido")
                false
            }
            else -> {
                uiState = uiState.copy(emailMessageError = null)
                true
            }
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return when {
            TextUtils.isEmpty(password) -> {
                uiState = uiState.copy(passwordMessageError = "Escribe tu contraseña")
                false
            }
            password.length < 8 -> {
                uiState =
                    uiState.copy(passwordMessageError = "Debe tener al menos 8 carapteres")
                false
            }
            else -> {
                uiState = uiState.copy(passwordMessageError = null)
                true
            }
        }
    }

    private fun isConfirmPasswordValid(password: String?, passwordConfirm: String?): Boolean {
        return when {
            TextUtils.isEmpty(passwordConfirm) -> {
                uiState =
                    uiState.copy(passwordConfirmMessageError = "Debes confirmar tu contraseña")
                false
            }
            !TextUtils.equals(password, passwordConfirm) -> {
                uiState =
                    uiState.copy(passwordConfirmMessageError = "Contraseñas no coinciden")
                false
            }
            else -> {
                uiState = uiState.copy(passwordConfirmMessageError = null)
                true
            }
        }
    }

}