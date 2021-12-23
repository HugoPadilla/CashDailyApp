package com.wenitech.cashdaily.framework.features.authentication.loginScreen.state

import javax.annotation.concurrent.Immutable

@Immutable
data class LoginUiState(
    val emailMessageError: String? = null,
    val passwordMessageError: String? = null,
    val isSuccessLogin: Boolean = false,
    val isLoadingLogin: Boolean = false,
    val isErrorLogin: Boolean = false,
    val isEnableButton: Boolean = false,
)
