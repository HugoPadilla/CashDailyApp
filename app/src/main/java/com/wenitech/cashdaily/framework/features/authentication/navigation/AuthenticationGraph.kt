package com.wenitech.cashdaily.framework.features.authentication.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wenitech.cashdaily.framework.features.authentication.StartAuthScreenViewModel
import com.wenitech.cashdaily.framework.features.authentication.StartAuthenticationScreen
import com.wenitech.cashdaily.framework.features.authentication.loginScreen.LoginScreen
import com.wenitech.cashdaily.framework.features.authentication.loginScreen.viewModel.LoginViewModel
import com.wenitech.cashdaily.framework.features.authentication.recoverPasswordScreen.RecoverPasswordScreen
import com.wenitech.cashdaily.framework.features.authentication.recoverPasswordScreen.RecoverPasswordViewModel
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.SignInScreen
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.viewModel.SignInViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
fun NavGraphBuilder.authenticationGraph(navController: NavHostController) {
    navigation(
        startDestination = AuthDestinations.Start.route,
        route = AuthDestinationsRoot
    ) {

        composable(AuthDestinations.Start.route) {
            val viewModel = hiltViewModel<StartAuthScreenViewModel>()

            if (viewModel.isUserAuthenticated.not()) {
                StartAuthenticationScreen(
                    onNavigationLoginWithEmail = {
                        navController.navigate(AuthDestinations.Login.route)
                    },
                    onNavigationLoginWithGoogle = {
                        /*TODO: 13/01/2022 Login with google account*/
                    }
                )
            }

        }

        composable(AuthDestinations.Login.route) {
            val viewModel = hiltViewModel<LoginViewModel>()

            LoginScreen(
                uiState = viewModel.uiState,
                email = viewModel.email.value,
                onEmailChange = viewModel::setEmail,
                password = viewModel.password.value,
                onPasswordChange = viewModel::setPassword,
                onDismissLoadingDialog = viewModel::onDismissLoadingDialog,
                onLogin = viewModel::doLogIn,
                onNavigateToRegister = {
                    navController.navigate(AuthDestinations.SingIn.route)
                },
                onNavigateToRecoverPassword = {
                    navController.navigate(AuthDestinations.RecoverPassword.route)
                }
            )
        }

        composable(AuthDestinations.SingIn.route) {
            val viewModel = hiltViewModel<SignInViewModel>()

            SignInScreen(
                onNavigationUp = { navController.navigateUp() },
                uiState = viewModel.uiState,
                email = viewModel.email,
                password = viewModel.password,
                passwordConfirm = viewModel.passwordConfirm,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onPasswordConfirmChange = viewModel::onPasswordConfirmChange,
                onSignInListener = viewModel::doSignIn,
                onDismissDialog = viewModel::onDismissDialog
            )
        }

        composable(AuthDestinations.RecoverPassword.route) {
            val viewModel = hiltViewModel<RecoverPasswordViewModel>()

            RecoverPasswordScreen(
                uiState = viewModel.uiState,
                onNavigationUp = { navController.navigateUp() },
                emailValue = viewModel.emailValue,
                onEmailValueChange = viewModel::emailValueChange,
                onNavigationRegister = { navController.navigate(AuthDestinations.SingIn.route) },
                onSendEmailClick = viewModel::sendEmailRecover,
                onDismissDialog = viewModel::dismissDialog
            )
        }
    }
}
