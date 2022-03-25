package com.wenitech.cashdaily.framework.features.authentication.recoverPasswordScreen

data class RecoverPasswordUiState(
    val isLoadingSendEmail: Boolean = false,
    val isSuccessSendEmail: Boolean = false,
    val isErrorMessage: String? = null,
    val emailMessageError: String? = null,
    val isEnableButton: Boolean = false,
)
