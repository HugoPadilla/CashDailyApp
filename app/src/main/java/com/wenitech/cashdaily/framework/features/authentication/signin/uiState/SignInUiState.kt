package com.wenitech.cashdaily.framework.features.authentication.signin.uiState

sealed class SignInUiState {
    object Loading: SignInUiState()
    object Success: SignInUiState()
    data class Collicion(val msg: String): SignInUiState()
    data class EmailMessageError(val msg: String?): SignInUiState()
    data class PasswordMessageError(val msg: String?): SignInUiState()
    data class PasswordConfirmMessageError(val msg: String?): SignInUiState()
    data class Error(val msg: String): SignInUiState()
}
