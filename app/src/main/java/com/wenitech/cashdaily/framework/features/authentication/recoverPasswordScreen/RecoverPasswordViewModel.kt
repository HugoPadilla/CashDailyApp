package com.wenitech.cashdaily.framework.features.authentication.recoverPasswordScreen

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.usecases.auth.RecoverPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverPasswordViewModel @Inject constructor(
    private val recoverPasswordUseCase: RecoverPasswordUseCase
) : ViewModel() {

    private val _emailRecover = MutableStateFlow("")
    val emailRecover: StateFlow<String> get() = _emailRecover

    private val _emailValueMessageError = MutableStateFlow<String?>(null)
    val emailValueMessageError: StateFlow<String?> get() = _emailValueMessageError

    // Result state
    private val _resultEmailRecover = Channel<ResultAuth<Boolean>>(Channel.BUFFERED)
    val resultEmailRecover = _resultEmailRecover.receiveAsFlow()

    fun emailValueChange(emailValue: String) {
        _emailRecover.value = emailValue
        isEmailValid(emailValue)
    }

    fun clearEditText() {
        _emailRecover.value = ""
    }

    fun sendEmailRecover(email: String) {
        viewModelScope.launch {
            recoverPasswordUseCase(email).collect {
                _resultEmailRecover.send(it)
            }
        }
    }

    private fun isEmailValid(email: String?): Boolean {
        var valid = true
        if (TextUtils.isEmpty(email)) {
            _emailValueMessageError.value = "Escribe tu correo"
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailValueMessageError.value = "Esto no es un correo valido"
            valid = false
        } else {
            _emailValueMessageError.value = null
        }
        return valid
    }

}