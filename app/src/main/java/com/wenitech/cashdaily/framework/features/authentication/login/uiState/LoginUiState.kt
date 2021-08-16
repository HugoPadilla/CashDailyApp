package com.wenitech.cashdaily.framework.features.authentication.login.uiState

sealed class LoginUiState {
    object Init : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val message: String) : LoginUiState()
    data class EmailMessageError(val message: String) : LoginUiState()
    data class PasswordMessageError(val message: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
