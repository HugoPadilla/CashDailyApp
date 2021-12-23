package com.wenitech.cashdaily.framework.features.authentication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.MainComposeActivity
import com.wenitech.cashdaily.framework.features.authentication.loginScreen.LoginScreen
import com.wenitech.cashdaily.framework.features.authentication.loginScreen.viewModel.LoginViewModel
import com.wenitech.cashdaily.framework.features.authentication.recoverPasswordScreen.RecoverPasswordScreen
import com.wenitech.cashdaily.framework.features.authentication.recoverPasswordScreen.RecoverPasswordViewModel
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.SignInScreen
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.state.ResultEnum
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.viewModel.SignInViewModel
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            CashDailyTheme {
                NavHost(
                    navController = navController,
                    startDestination = AuthDestinations.Start.route
                ) {

                    composable(AuthDestinations.Start.route) {
                        AuthenticationHomeScreen(onNavigation = {
                            navController.navigate(it)
                        })
                    }

                    composable(AuthDestinations.Login.route) {
                        val viewModel = hiltViewModel<LoginViewModel>()

                        LaunchedEffect(key1 = Unit, block = {
                            viewModel.state.collectLatest {
                                when (it.onSuccess) {
                                    true -> startActivityMain()
                                    false -> return@collectLatest
                                }
                            }
                        })

                        LoginScreen(
                            state = viewModel.state.collectAsState().value,
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
                        val viewModel: SignInViewModel = hiltViewModel()

                        LaunchedEffect(key1 = Unit) {
                            viewModel.state.collectLatest {
                                when (it.result) {
                                    ResultEnum.Success -> startActivityMain()
                                    else -> return@collectLatest
                                }
                            }
                        }

                        SignInScreen(
                            onNavigationUp = { navController.navigateUp() },
                            state = viewModel.state.collectAsState().value,
                            email = viewModel.email.value,
                            password = viewModel.password.value,
                            passwordConfirm = viewModel.passwordConfirm.value,
                            onEmailChange = viewModel::onEmailChange,
                            onPasswordChange = viewModel::onPasswordChange,
                            onPasswordConfirmChange = viewModel::onPasswordConfirmChange,
                            onSignInListener = viewModel::doSignIn,
                            onDismissDialog = viewModel::onDismissDialog
                        )
                    }

                    composable(AuthDestinations.RecoverPassword.route) {
                        val viewModel: RecoverPasswordViewModel = hiltViewModel()

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
        }
    }

    @ExperimentalAnimationApi
    private fun startActivityMain() {
        val intent = Intent(
            this@AuthenticationActivity,
            MainComposeActivity::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

}

@Composable
fun AuthenticationHomeScreen(onNavigation: (route: String) -> Unit) {
    Scaffold(modifier = Modifier) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(bottom = 42.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_modey_transparent),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.8f)
            )

            Button(
                onClick = { onNavigation(AuthDestinations.Login.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Continua con Email")
            }

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Continua con Google")
            }

        }
    }
}
