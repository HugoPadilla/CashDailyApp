package com.wenitech.cashdaily.framework.features.authentication.loginScreen.state

import javax.annotation.concurrent.Immutable

@Immutable
data class LoginState(
    val emailMessageError: String? = null,
    val passwordMessageError: String? = null,
    val onSuccess: Boolean = false,
    val shoDialogLoading: Boolean = false,
    val shoDialogError: Boolean = false,
    val buttonEnable: Boolean = false,
)
