package com.wenitech.cashdaily.framework.features.authentication.loginScreen.uiState

sealed class LoginUiState {
    object Init: LoginUiState()
    object Loading: LoginUiState()
    object Success: LoginUiState()
    data class Failed(val msg: String): LoginUiState()
}
