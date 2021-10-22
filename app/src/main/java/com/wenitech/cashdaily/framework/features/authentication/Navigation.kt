package com.wenitech.cashdaily.framework.features.authentication

sealed class Navigation(val route: String){
    object Start : Navigation("start_login")
    object Login : Navigation("login")
    object SingIn : Navigation("sing_in")
    object RecoverPassword : Navigation("recover_password")
}
