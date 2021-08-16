package com.wenitech.cashdaily.framework.features.authentication.signin.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.commons.Status
import com.wenitech.cashdaily.domain.usecases.auth.SignInUseCase
import com.wenitech.cashdaily.framework.features.authentication.signin.event.SignInEvent
import com.wenitech.cashdaily.framework.features.authentication.signin.uiState.SignInUiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignInViewModel @ViewModelInject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    val _email = MutableLiveData<String>()
    val _password = MutableLiveData<String>()
    val _passwordConfirm = MutableLiveData<String>()

    private val _signInUiState = MutableLiveData<SignInUiState>()
    val signInUiState: LiveData<SignInUiState> = _signInUiState


    fun process(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInClicked -> {
                doSignIn(event.email, event.password, event.passwordConfirm)
            }
        }
    }

    private fun doSignIn(email: String, password: String, passwordConfirm: String) {
        if (isEmailValid(email) && isPasswordValid(password) &&
            isConfirPasswordValid(password, passwordConfirm)
        ) {
            viewModelScope.launch {
                signInUseCase("USER_NAME", email, password).collect {
                    when (it.status) {
                        Status.LOADING -> _signInUiState.value = SignInUiState.Loading
                        Status.SUCCESS -> _signInUiState.value = SignInUiState.Success
                        Status.COLLICION -> _signInUiState.value =
                            SignInUiState.Collicion(it.messenger.toString())
                        Status.FAILED -> _signInUiState.value =
                            SignInUiState.Error(it.messenger.toString())
                    }
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        var valid = true
        if (TextUtils.isEmpty(email)) {
            _signInUiState.value = SignInUiState.EmailMessageError("Escribe un correo")
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _signInUiState.value = SignInUiState.EmailMessageError("Este no es un correo valido")
            valid = false
        } else {
            _signInUiState.value = SignInUiState.EmailMessageError(null)
        }
        return valid
    }

    private fun isPasswordValid(password: String): Boolean {
        var valid = true
        when {
            TextUtils.isEmpty(password) -> {
                _signInUiState.value = SignInUiState.PasswordMessageError("Escribe tu contraseña")
                valid = false
            }
            password.length < 8 -> {
                _signInUiState.value = SignInUiState.PasswordMessageError("Debe tener al menos 8 carapteres")
                valid = false
            }
            else -> {
                _signInUiState.value = SignInUiState.PasswordMessageError(null)
            }
        }
        return valid
    }

    private fun isConfirPasswordValid(password: String?, passwordConfirm: String?): Boolean {
        var valid = true

        if (TextUtils.isEmpty(passwordConfirm)) {
            _signInUiState.value = SignInUiState.PasswordConfirmMessageError("Debes confirmar tu contraseña")
            valid = false
        } else if (!TextUtils.equals(password, passwordConfirm)) {
            _passwordConfirm.value = ""
            _signInUiState.value = SignInUiState.PasswordConfirmMessageError("Contraseñas no coinciden")
            valid = false
        } else {
            _signInUiState.value = SignInUiState.PasswordMessageError(null)
            _signInUiState.value = SignInUiState.PasswordConfirmMessageError(null)
        }
        return valid
    }


}