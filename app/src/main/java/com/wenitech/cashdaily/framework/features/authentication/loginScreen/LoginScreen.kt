package com.wenitech.cashdaily.framework.features.authentication.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.component.commons.CustomTextField
import com.wenitech.cashdaily.framework.component.commons.TextButtonRegister
import com.wenitech.cashdaily.framework.features.authentication.Navigation
import com.wenitech.cashdaily.framework.features.authentication.loginScreen.uiState.LoginUiState
import com.wenitech.cashdaily.framework.features.authentication.loginScreen.viewModel.LoginViewModel
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    startActivityMain: () -> Unit
) {

    val email by viewModel.email.collectAsState()
    val emailMessageError by viewModel.emailMessageError.collectAsState()

    val password by viewModel.password.collectAsState()
    val passwordMessageError by viewModel.passwordMessageError.collectAsState()

    val isValidEmail = viewModel.isValidEmail.collectAsState()
    val isValidPassword = viewModel.isValidPassword.collectAsState()

    val isValidForm by derivedStateOf { isValidEmail.value && isValidPassword.value }

    val loginUiState by viewModel.loginUiState.collectAsState()

    when (loginUiState) {
        is LoginUiState.Failed -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Error al iniciar sesion")
            }
        }
        LoginUiState.Init -> {
            LoginContent(
                emailValue = email,
                emailMessageError = emailMessageError,
                passwordValue = password,
                passwordMessageError = passwordMessageError,
                isValidForm = isValidForm,
                onEmailChange = { viewModel.onEmailChange(it) },
                onPasswordChange = { viewModel.onPasswordChange(it) },
                onLoginListener = { email, password ->
                    viewModel.doLogIn(email = email, password = password)
                },
                onNavigateRegisterListener = { route ->
                    navController.navigate(route)
                }
            )
        }
        LoginUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Loading...")
            }
        }
        LoginUiState.Success -> {
            startActivityMain()
        }
    }
}

@Composable
private fun LoginContent(
    emailValue: String,
    emailMessageError: String? = null,
    passwordValue: String,
    passwordMessageError: String? = null,
    isValidForm: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginListener: (email: String, password: String) -> Unit,
    onNavigateRegisterListener: (route: String) -> Unit
) {

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValue ->

        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValue)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight(0.4f),
                painter = painterResource(id = R.drawable.logo_modey_transparent),
                contentDescription = null
            )

            CustomTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = "Correo electronico",
                value = emailValue,
                messageError = emailMessageError,
                icon = R.drawable.ic_mail,
                onValueChange = onEmailChange
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = "Contrasena",
                value = passwordValue,
                messageError = passwordMessageError,
                icon = R.drawable.ic_lock,
                onValueChange = onPasswordChange
            )

            Text(
                modifier = Modifier
                    .padding(top = 24.dp, start = 0.dp)
                    .height(32.dp)
                    .clickable { onNavigateRegisterListener(Navigation.RecoverPassword.route) },
                style = MaterialTheme.typography.body1,
                text = "Recuperar contraseña",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .padding(horizontal = 16.dp),
                onClick = { onLoginListener(emailValue, passwordValue) },
                enabled = isValidForm
            ) {
                Text(text = "INICIAR SESION")
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButtonRegister(
                modifier = Modifier.padding(bottom = 32.dp),
                onNavigationRegisterListener = {
                    onNavigateRegisterListener(Navigation.SingIn.route)
                }, textButton = "Registrate"
            )

        }
    }
}

@Composable
fun ShowAlertDialog(
    showDialog: Boolean,
    title: String,
    text: String,
    textPositive: String? = null,
    textNegative: String? = null,
    onDismissRequest: () -> Unit,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit
) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismissRequest()
            },
            title = { Text(text = title) },
            text = { Text(text = text) },
            confirmButton = {
                if (!textPositive.isNullOrBlank()) {
                    Button(onClick = { onPositiveClick() }) {
                        Text(text = textPositive)
                    }
                }
            },
            dismissButton = {
                if (!textNegative.isNullOrBlank()) {
                    Button(onClick = { onNegativeClick() }) {
                        Text(text = textNegative)
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    CashDailyTheme {
        LoginContent(
            emailValue = "",
            passwordValue = "",
            isValidForm = false,
            onEmailChange = {},
            onPasswordChange = {},
            onLoginListener = { _, _ -> },
            onNavigateRegisterListener = {}
        )
    }
}