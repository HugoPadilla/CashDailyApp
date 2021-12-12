package com.wenitech.cashdaily.framework.features.authentication.signinScreen.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.usecases.auth.SignInEmailUseCase
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.uiState.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInEmailUseCase: SignInEmailUseCase
) : ViewModel() {

    // Email used in EditText
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _emailMessageError = MutableStateFlow<String?>(null)
    val emailMessageError: StateFlow<String?> get() = _emailMessageError

    // Password used in EditText
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _passwordMessageError = MutableStateFlow<String?>(null)
    val passwordMessageError: StateFlow<String?> get() = _passwordMessageError

    // Password confirm used in EditText
    private val _passwordConfirm = MutableStateFlow("")
    val passwordConfirm: StateFlow<String> get() = _passwordConfirm

    private val _passwordConfirmMessageError = MutableStateFlow<String?>(null)
    val passwordConfirmMessageError: StateFlow<String?> get() = _passwordConfirmMessageError

    // Valid of EditText
    private val _isValidEmail = MutableStateFlow(false)
    val isValidEmail: StateFlow<Boolean> get() = _isValidEmail

    private val _isValidPassword = MutableStateFlow(false)
    val isValidPassword: StateFlow<Boolean> get() = _isValidPassword

    private val _isValidPasswordConfirm = MutableStateFlow(false)
    val isValidPasswordConfirm: StateFlow<Boolean> get() = _isValidPasswordConfirm

    // Result state
    private val _signInUiState = Channel<SignInUiState>(Channel.BUFFERED)
    val signInUiState = _signInUiState.receiveAsFlow()


    fun onEmailChange(email: String) {
        _email.value = email
        isEmailValid(email = email)
    }

    fun onPasswordChange(password: String) {
        _password.value = password
        isPasswordValid(password = password)
    }

    fun onPasswordConfirmChange(passwordConfirm: String) {
        _passwordConfirm.value = passwordConfirm
        isConfirmPasswordValid(password = password.value, passwordConfirm = passwordConfirm)
    }

    fun doSignIn(email: String, password: String) {
        viewModelScope.launch {
            signInEmailUseCase("USER_NAME", email, password).collect {
                when (it.status) {
                    com.wenitech.cashdaily.domain.common.Status.LOADING -> _signInUiState.send(
                        SignInUiState.Loading
                    )
                    com.wenitech.cashdaily.domain.common.Status.SUCCESS -> _signInUiState.send(
                        SignInUiState.Success
                    )
                    com.wenitech.cashdaily.domain.common.Status.COLLICION -> _signInUiState.send(
                        SignInUiState.Collision(it.messenger.toString())
                    )
                    com.wenitech.cashdaily.domain.common.Status.FAILED -> _signInUiState.send(
                        SignInUiState.Failed(it.messenger.toString())
                    )
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        var valid = true
        if (TextUtils.isEmpty(email)) {
            _emailMessageError.value = ("Escribe un correo")
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailMessageError.value = ("Este no es un correo valido")
            valid = false
        } else {
            _emailMessageError.value = (null)
        }
        _isValidEmail.value = valid
        return valid
    }

    private fun isPasswordValid(password: String): Boolean {
        var valid = true
        when {
            TextUtils.isEmpty(password) -> {
                _passwordMessageError.value = ("Escribe tu contraseña")
                valid = false
            }
            password.length < 8 -> {
                _passwordMessageError.value = ("Debe tener al menos 8 carapteres")
                valid = false
            }
            else -> {
                _passwordMessageError.value = (null)
            }
        }
        _isValidPassword.value = valid
        return valid
    }

    private fun isConfirmPasswordValid(password: String?, passwordConfirm: String?): Boolean {
        var valid = true

        if (TextUtils.isEmpty(passwordConfirm)) {
            _passwordConfirmMessageError.value = ("Debes confirmar tu contraseña")
            valid = false
        } else if (!TextUtils.equals(password, passwordConfirm)) {
            _passwordConfirmMessageError.value = ("Contraseñas no coinciden")
            valid = false
        } else {
            _passwordMessageError.value = (null)
            _passwordConfirmMessageError.value = (null)
        }
        _isValidPasswordConfirm.value = valid
        return valid
    }

}