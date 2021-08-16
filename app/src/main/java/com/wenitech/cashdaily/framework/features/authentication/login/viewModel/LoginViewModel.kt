package com.wenitech.cashdaily.framework.features.authentication.login.viewModel

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.wenitech.cashdaily.commons.Status.*
import com.wenitech.cashdaily.domain.usecases.auth.LoginUseCase
import com.wenitech.cashdaily.framework.features.authentication.login.event.LoginEvent
import com.wenitech.cashdaily.framework.features.authentication.login.uiState.LoginUiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    val _email = MutableLiveData<String>()
    val _password = MutableLiveData<String>()

    // State view of Login
    private val _loginUiState = MutableLiveData<LoginUiState>(LoginUiState.Init)
    val loginUiState: LiveData<LoginUiState> get() = _loginUiState


    fun process(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginClicked -> doLogIn(event.email, event.password)
        }
    }

    private fun doLogIn(email: String, password: String) {
        if (isEmailValid(email) && isPasswordValid(password)) {
            viewModelScope.launch {
                try {
                    loginUseCase(email, password).collect {
                        when (it.status) {
                            LOADING -> {
                                _loginUiState.value = LoginUiState.Loading
                            }
                            SUCCESS -> {
                                _loginUiState.value = LoginUiState.Success(it.dato.toString())
                            }
                            COLLICION -> {
                                // Todo:
                            }
                            FAILED -> {
                                _loginUiState.value = LoginUiState.Error(it.messenger.toString())
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
            _loginUiState.value = LoginUiState.EmailMessageError("Escribe tu correo")
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginUiState.value = LoginUiState.EmailMessageError("Esto no es un correo valido")
            valid = false
        } else {
            _loginUiState.value = LoginUiState.EmailMessageError("")
        }
        return valid
    }

    private fun isPasswordValid(password: String?): Boolean {
        var valid = true
        when {
            TextUtils.isEmpty(password) -> {
                _loginUiState.value = LoginUiState.PasswordMessageError("Escribe tu contraseña")
                valid = false
            }
            password!!.length < 8 -> {
                _loginUiState.value = LoginUiState.PasswordMessageError("Debe tener al menos 8 carapteres")
                valid = false
            }
            else -> {
                _loginUiState.value = LoginUiState.PasswordMessageError("")
            }
        }
        return valid
    }

}