package com.wenitech.cashdaily.features.authentication.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.wenitech.cashdaily.data.model.User
import com.wenitech.cashdaily.domain.interaction.auth.LoginUseCase
import com.wenitech.cashdaily.domain.interaction.auth.SignInUseCase
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.core.Status

class LoginViewModel @ViewModelInject constructor(
        private val loginUseCase: LoginUseCase,
        private val signInUseCase: SignInUseCase,
) : ViewModel() {
    // Edit text fragment login
    val _nameUser = MutableLiveData<String>()
    val _nameBussine = MutableLiveData<String>()
    var _email: MutableLiveData<String> = MutableLiveData()
    var _password: MutableLiveData<String> = MutableLiveData()
    var _passwordConfirm: MutableLiveData<String> = MutableLiveData()

    // Messenger Edit Text
    private var _emailMessageError: MutableLiveData<String> = MutableLiveData()
    val emailMessengerError: LiveData<String> get() = _emailMessageError

    private var _passwordMessageError: MutableLiveData<String> = MutableLiveData()
    val passwordMessageError: LiveData<String> get() = _passwordMessageError

    private var _PasswordConfirmMessageError: MutableLiveData<String> = MutableLiveData()
    val passwordConfirmMessageError: LiveData<String> get() = _PasswordConfirmMessageError

    // state del Login y Sing in
    private var _resulLogin: MutableLiveData<ResourceAuth<String>> = MutableLiveData()
    val resulLogin: LiveData<ResourceAuth<String>> get() = _resulLogin

    private var _resulSingUp: MutableLiveData<ResourceAuth<String>> = MutableLiveData()
    val resultSingIn: LiveData<ResourceAuth<String>> get() = _resulSingUp



    fun logIn() {
        val email = _email.value.toString().trim()
        val password = _password.value.toString().trim()

        if (isEmailValid(email) && isPasswordValid(password)) {

            _resulLogin.value = ResourceAuth(Status.LOADING, null, null)

            loginUseCase.execute(email, password).observeForever {
                _resulLogin.value = it
            }
        }
    }

    fun signIn() {
        val email = _email.value.toString().trim()
        val password = _password.value.toString().trim()
        val passwordConfirm = _passwordConfirm.value

        if (isEmailValid(email) && isPasswordValid(password) && isConfirPasswordValid(password, passwordConfirm)) {

            _resulSingUp.value = ResourceAuth(Status.LOADING, null, null)

            // nuevo objeto usuario
            val userApp = User()
            userApp.email = email
            userApp.nameBussine = "NAME_BUSSINE"
            userApp.nameUser = "NAME_USER"
            userApp.urlPhoto = "URL_PHOTO"

            signInUseCase.execute(email, password, userApp).observeForever(Observer {
                _resulSingUp.value = it
            })
        }
    }

    fun onCancelSingIn() {
        _emailMessageError.value = ""
        _password.value = ""
        _passwordMessageError.value = ""
        _passwordConfirm.value = ""
        _PasswordConfirmMessageError.value = ""
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
            _emailMessageError.setValue("")
        }
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
                _passwordMessageError.setValue("")
            }
        }
        return valid
    }

    private fun isConfirPasswordValid(password: String?, passwordConfirm: String?): Boolean {
        var valid = true

        if (TextUtils.isEmpty(passwordConfirm)) {
            this._PasswordConfirmMessageError.value = "Debes confirmar tu contraseña"
            valid = false
        } else if (!TextUtils.equals(password, passwordConfirm)) {
            _passwordConfirm.value = ""
            _PasswordConfirmMessageError.value = "Contraseñas no coinciden"
            valid = false
        } else {
            _passwordMessageError.value = ""
            _PasswordConfirmMessageError.setValue("")
        }
        return valid
    }


}