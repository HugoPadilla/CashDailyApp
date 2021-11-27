package com.wenitech.cashdaily.framework.features.authentication

sealed class AuthDestinations(val route: String){
    object Start : AuthDestinations("start_login")
    object Login : AuthDestinations("login")
    object SingIn : AuthDestinations("sing_in")
    object RecoverPassword : AuthDestinations("recover_password")
}
