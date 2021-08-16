package com.wenitech.cashdaily.framework.features.authentication.login.event

sealed class LoginEvent {
    class LoginClicked(val email: String, val password: String) : LoginEvent()
}
