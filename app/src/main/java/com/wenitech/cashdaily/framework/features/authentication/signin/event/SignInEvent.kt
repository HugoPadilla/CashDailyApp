package com.wenitech.cashdaily.framework.features.authentication.signin.event

sealed class SignInEvent {
    class SignInClicked(
        val email: String, val password: String, val passwordConfirm: String
    ) : SignInEvent()
}
