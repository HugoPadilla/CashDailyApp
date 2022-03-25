package com.wenitech.cashdaily.framework.features.authentication.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.component.button.TextButtonRegister
import com.wenitech.cashdaily.framework.component.edittext.CustomTextField
import com.wenitech.cashdaily.framework.features.authentication.loginScreen.state.LoginUiState
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onDismissLoadingDialog: () -> Unit,
    onLogin: (email: String, password: String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToRecoverPassword: () -> Unit,
) {

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val (isPasswordVisible, onPasswordVisible) = remember { mutableStateOf(false) }
    val iconPassword = if (isPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye_off

    if (uiState.isErrorLogin) {
        AlertDialog(
            onDismissRequest = onDismissLoadingDialog,
            text = { Text(text = "Correo o contraseña invalidos. Revisa tus credenciales") },
            confirmButton = {
                TextButton(onClick = onDismissLoadingDialog) {
                    Text(text = "Revisar")
                }
            }
        )
    }

    if (uiState.isLoadingLogin) {
        AlertDialog(
            onDismissRequest = {},
            text = { Text(text = "Iniciando sesion...") },
            confirmButton = {}
        )
    }

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
                value = email,
                onValueChange = onEmailChange,
                label = "Correo electronico",
                modifier = Modifier.padding(horizontal = 16.dp),
                messageError = uiState.emailMessageError,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mail),
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = "Contrasena",
                modifier = Modifier.padding(horizontal = 16.dp),
                messageError = uiState.passwordMessageError,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { onPasswordVisible(!isPasswordVisible) }) {
                        Icon(
                            painter = painterResource(id = iconPassword),
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(onSend = {
                    onLogin(email, password)
                })
            )

            Text(
                modifier = Modifier
                    .padding(top = 24.dp, start = 0.dp)
                    .height(32.dp)
                    .clickable { onNavigateToRecoverPassword() },
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
                onClick = { onLogin(email, password) },
                enabled = uiState.isEnableButton
            ) {
                Text(text = "INICIAR SESION")
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButtonRegister(
                modifier = Modifier.padding(bottom = 32.dp),
                onNavigationRegisterListener = {
                    onNavigateToRegister()
                },
                textButton = "Registrate"
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    CashDailyTheme {
        LoginScreen(
            uiState = LoginUiState(),
            email = "",
            onEmailChange = {},
            password = "",
            onPasswordChange = {},
            onLogin = { _, _ -> },
            onNavigateToRegister = {},
            onDismissLoadingDialog = {},
            onNavigateToRecoverPassword = {}
        )
    }
}