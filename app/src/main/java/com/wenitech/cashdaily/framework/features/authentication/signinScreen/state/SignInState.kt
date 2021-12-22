package com.wenitech.cashdaily.framework.features.authentication.signinScreen.state

data class SignInState(
    val emailMessageError: String? = null,
    val passwordMessageError: String? = null,
    val passwordConfirmMessageError: String? = null,
    val result: ResultEnum = ResultEnum.Init,
    val buttonEnable: Boolean = false,
)

enum class ResultEnum {
    Init,
    Failed,
    Collision,
    Loading,
    Success,
}
