package com.wenitech.cashdaily.framework.features.authentication.signinScreen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.component.appBar.CustomAppBar
import com.wenitech.cashdaily.framework.component.dialog.ShowAlertDialog
import com.wenitech.cashdaily.framework.component.edittext.CustomTextField
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.state.ResultEnum.*
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.state.SignInUiState
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SignInScreen(
    onNavigationUp: () -> Unit,
    uiState: SignInUiState,
    email: String,
    password: String,
    passwordConfirm: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
    onDismissDialog: () -> Unit,
    onSignInListener: (email: String, password: String) -> Unit
) {

    val scroll = rememberScrollState()
    val focusManage = LocalFocusManager.current

    val (passwordVisible, onPasswordVisible) = remember { mutableStateOf(false) }
    val iconPassword = if (passwordVisible) R.drawable.ic_eye else R.drawable.ic_eye_off
    val visualTransformationPassword =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    val (passwordConfirmVisible, onPasswordConfirmVisible) = remember { mutableStateOf(false) }
    val iconPasswordConfirm =
        if (passwordConfirmVisible) R.drawable.ic_eye else R.drawable.ic_eye_off
    val visualTransformationPasswordConfirm =
        if (passwordConfirmVisible) VisualTransformation.None else PasswordVisualTransformation()

    when (uiState.result) {
        Failed -> {
            ShowAlertDialog(
                showDialog = true,
                title = stringResource(id = R.string.sign_in_error_dialog_title),
                text = stringResource(id = R.string.sign_in_error_dialog),
                textPositive = stringResource(id = R.string.sign_in_error_dialog_text_positive),
                onDismissRequest = onDismissDialog,
                onPositiveClick = onDismissDialog
            ) {

            }
        }
        Collision -> {
            AlertDialog(onDismissRequest = onDismissDialog,
                title = { Text(text = stringResource(id = R.string.sign_in_collision_dialog_title)) },
                text = {
                    Text(
                        text = stringResource(id = R.string.sign_in_collision_dialog_text)
                    )
                },
                confirmButton = {
                    TextButton(onClick = onDismissDialog) {
                        Text(text = stringResource(id = R.string.sign_in_collision_dialog_confirm_button))
                    }
                }
            )
        }
        Loading -> {
            AlertDialog(
                onDismissRequest = {},
                text = { Text(text = stringResource(id = R.string.sign_in_loading_text)) },
                buttons = {}
            )
        }
        else -> Unit
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomAppBar(leadingIcon = R.drawable.ic_arrow_left) {
                onNavigationUp()
            }
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scroll)
                .padding(paddingValue),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .padding(top = 42.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight(600),
                text = stringResource(id = R.string.sign_in_title),
            )

            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                style = MaterialTheme.typography.body1,
                text = stringResource(id = R.string.sign_in_text),
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                CustomTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = stringResource(id = R.string.sign_in_email),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mail),
                            contentDescription = null
                        )
                    },
                    messageError = uiState.emailMessageError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManage.moveFocus(
                            FocusDirection.Down
                        )
                    })
                )

                CustomTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = stringResource(id = R.string.sign_in_password),
                    messageError = uiState.passwordMessageError,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lock),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { onPasswordVisible(passwordVisible.not()) }) {
                            Icon(
                                painter = painterResource(id = iconPassword),
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = visualTransformationPassword,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManage.moveFocus(
                            FocusDirection.Down
                        )
                    })
                )

                CustomTextField(
                    value = passwordConfirm,
                    onValueChange = onPasswordConfirmChange,
                    label = stringResource(id = R.string.sign_in_password_confirn),
                    messageError = uiState.passwordConfirmMessageError,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lock),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { onPasswordConfirmVisible(passwordConfirmVisible.not()) }) {
                            Icon(
                                painter = painterResource(id = iconPasswordConfirm),
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = visualTransformationPasswordConfirm,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        onSignInListener(email, password)
                    })
                )

                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.termn)
                )

            }

            Button(
                enabled = uiState.buttonEnable,
                onClick = { onSignInListener(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(46.dp)
            ) {
                Text(text = stringResource(id = R.string.sign_in_button))
            }

        }
    }

}

@ExperimentalCoroutinesApi
@Preview
@Composable
fun PreviewSignIn() {
    CashDailyTheme {
        SignInScreen(
            onNavigationUp = {},
            uiState = SignInUiState(),
            email = "",
            password = "",
            passwordConfirm = "",
            onEmailChange = {},
            onPasswordChange = {},
            onPasswordConfirmChange = {},
            onSignInListener = { _, _ -> },
            onDismissDialog = {}
        )
    }
}