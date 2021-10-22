package com.wenitech.cashdaily.framework.features.authentication.signinScreen.uiState

sealed class SignInUiState {
    object Loading : SignInUiState()
    object Success : SignInUiState()
    data class Collision(val msg: String) : SignInUiState()
    data class Failed(val msg: String) : SignInUiState()
}
