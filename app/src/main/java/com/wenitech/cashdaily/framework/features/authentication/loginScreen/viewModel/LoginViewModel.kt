package com.wenitech.cashdaily.framework.features.authentication.loginScreen.viewModel

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.commons.Status.*
import com.wenitech.cashdaily.domain.usecases.auth.LoginUseCase
import com.wenitech.cashdaily.framework.features.authentication.loginScreen.uiState.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _emailMessageError = MutableStateFlow<String?>(null)
    val emailMessageError: StateFlow<String?> get() = _emailMessageError

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _passwordMessageError = MutableStateFlow<String?>(null)
    val passwordMessageError: StateFlow<String?> get() = _passwordMessageError

    private val _isValidEmail = MutableStateFlow(false)
    val isValidEmail: StateFlow<Boolean> get() = _isValidEmail

    private val _isValidPassword = MutableStateFlow(false)
    val isValidPassword: StateFlow<Boolean> get() = _isValidPassword

    val loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Init)

    fun doLogIn(email: String, password: String) {
        if (isEmailValid(email) && isPasswordValid(password)) {
            viewModelScope.launch {
                try {
                    loginUseCase(email, password).collect {
                        when (it.status) {
                            LOADING -> {
                                loginUiState.value = LoginUiState.Loading
                            }
                            SUCCESS -> {
                                loginUiState.value =LoginUiState.Success
                            }
                            COLLICION -> {
                                // Not used
                            }
                            FAILED -> {
                                loginUiState.value =LoginUiState.Failed(it.messenger.toString())
                            }
                        }
                    }
                } catch (e: Throwable) {
                    Log.d("TAG", "logIn: ${e.message.toString()}")
                }
            }
        }
    }

    private fun isEmailValid(email: String?): Boolean {
        var valid = true
        if (TextUtils.isEmpty(email)) {
            _emailMessageError.value = "Escribe tu correo"
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailMessageError.value = "Esto no es un correo valido"
            valid = false
        } else {
            _emailMessageError.value = null
        }
        _isValidEmail.value = valid
        return valid
    }

    private fun isPasswordValid(password: String?): Boolean {
        var valid = true
        when {
            TextUtils.isEmpty(password) -> {
                _passwordMessageError.value = "Escribe tu contraseña"
                valid = false
            }
            password!!.length < 8 -> {
                _passwordMessageError.value = "Debe tener al menos 8 carapteres"
                valid = false
            }
            else -> {
                _passwordMessageError.value = null
            }
        }
        _isValidPassword.value = valid
        return valid
    }

    fun onEmailChange(email: String) {
        _email.value = email
        isEmailValid(email)
    }

    fun onPasswordChange(password: String) {
        _password.value = password
        isPasswordValid(password)
    }

}