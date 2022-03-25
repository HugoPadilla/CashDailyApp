package com.wenitech.cashdaily.framework.features.authentication.navigation

const val AuthDestinationsRoot = "auth_root"

sealed class AuthDestinations(val route: String){
    object Start : AuthDestinations("start_auth")
    object Login : AuthDestinations("login")
    object SingIn : AuthDestinations("sing_in")
    object RecoverPassword : AuthDestinations("recover_password")
}
