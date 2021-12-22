package com.wenitech.cashdaily.framework.features.authentication.signinScreen.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Status
import com.wenitech.cashdaily.domain.usecases.auth.SignInEmailUseCase
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.state.ResultEnum
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInEmailUseCase: SignInEmailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var passwordConfirm = mutableStateOf("")
        private set

    init {
        snapshotFlow {
            email.value to password.value to passwordConfirm.value
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
        this.email.value = email
    }

    fun onPasswordChange(password: String) {
        this.password.value = password
    }

    fun onPasswordConfirmChange(passwordConfirm: String) {
        this.passwordConfirm.value = passwordConfirm
    }

    fun onDismissDialog() {
        _state.value = _state.value.copy(result = ResultEnum.Init)
    }

    private fun setEnableButton(enable: Boolean) {
        _state.value = _state.value.copy(buttonEnable = enable)
    }

    fun doSignIn(email: String, password: String) {
        viewModelScope.launch {
            signInEmailUseCase("USER_NAME", email, password).collect {
                when (it.status) {
                    Status.LOADING -> _state.value = _state
                        .value.copy(result = ResultEnum.Loading)
                    Status.SUCCESS -> _state.value = _state
                        .value.copy(result = ResultEnum.Success)
                    Status.COLLICION -> _state.value = _state
                        .value.copy(result = ResultEnum.Collision)
                    Status.FAILED -> _state.value = _state
                        .value.copy(result = ResultEnum.Failed)
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                _state.value = _state.value.copy(emailMessageError = "Escribe un correo")
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _state.value = _state.value.copy(emailMessageError = "Este no es un correo valido")
                false
            }
            else -> {
                _state.value = _state.value.copy(emailMessageError = null)
                true
            }
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return when {
            TextUtils.isEmpty(password) -> {
                _state.value = _state.value.copy(passwordMessageError = "Escribe tu contraseña")
                false
            }
            password.length < 8 -> {
                _state.value =
                    _state.value.copy(passwordMessageError = "Debe tener al menos 8 carapteres")
                false
            }
            else -> {
                _state.value = _state.value.copy(passwordMessageError = null)
                true
            }
        }
    }

    private fun isConfirmPasswordValid(password: String?, passwordConfirm: String?): Boolean {
        return when {
            TextUtils.isEmpty(passwordConfirm) -> {
                _state.value =
                    _state.value.copy(passwordConfirmMessageError = "Debes confirmar tu contraseña")
                false
            }
            !TextUtils.equals(password, passwordConfirm) -> {
                _state.value =
                    _state.value.copy(passwordConfirmMessageError = "Contraseñas no coinciden")
                false
            }
            else -> {
                _state.value = _state.value.copy(passwordConfirmMessageError = null)
                true
            }
        }
    }

}