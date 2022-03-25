package com.wenitech.cashdaily.framework.features.authentication.recoverPasswordScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.component.appBar.CustomAppBar
import com.wenitech.cashdaily.framework.component.button.PrimaryButton
import com.wenitech.cashdaily.framework.component.button.TextButtonRegister
import com.wenitech.cashdaily.framework.component.edittext.CustomTextField
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun RecoverPasswordScreen(
    uiState: RecoverPasswordUiState,
    emailValue: String,
    onEmailValueChange: (email: String) -> Unit,
    onNavigationUp: () -> Unit,
    onNavigationRegister: () -> Unit,
    onSendEmailClick: (email: String) -> Unit,
    onDismissDialog: () -> Unit
) {

    if (uiState.isSuccessSendEmail) {
        AlertDialog(
            onDismissRequest = onNavigationUp,
            title = {
                Text(text = stringResource(id = R.string.recover_password_dialog_success_title))
            },
            text = {
                Text(text = stringResource(id = R.string.recover_password_dialog_success_text))
            },
            confirmButton = {
                TextButton(onClick = onNavigationUp) {
                    Text(text = stringResource(id = R.string.recover_password_dialog_success_confirm_button))
                }
            }
        )
    }

    if (uiState.isLoadingSendEmail) {
        AlertDialog(
            onDismissRequest = onDismissDialog,
            text = {
                Text(text = stringResource(id = R.string.recover_password_dialog_loading_loading))
            },
            buttons = {}
        )
    }

    uiState.isErrorMessage?.let {
        AlertDialog(
            onDismissRequest = onDismissDialog,
            title = {
                Text(text = stringResource(id = R.string.recover_password_dialog_error_title))
            },
            text = {
                Text(text = it)
            },
            confirmButton = {
                TextButton(onClick = onDismissDialog) {
                    Text(text = stringResource(id = R.string.recover_password_dialog_success_confirm_button))
                }
            }
        )
    }

    val scrollSate = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomAppBar(leadingIcon = R.drawable.ic_arrow_left) {
                onNavigationUp()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scrollSate),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(top = 42.dp, bottom = 12.dp),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight(600),
                text = stringResource(id = R.string.recover_password_title)
            )

            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                style = MaterialTheme.typography.body1,
                text = stringResource(id = R.string.recover_password_description)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                label = stringResource(id = R.string.recover_password_email),
                value = emailValue,
                messageError = uiState.emailMessageError,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mail),
                        contentDescription = null
                    )
                },
                onValueChange = onEmailValueChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                onClick = { onSendEmailClick(emailValue) },
                enabled = uiState.isEnableButton,
                text = stringResource(id = R.string.recover_password_button),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButtonRegister(
                onNavigationRegisterListener = { onNavigationRegister() },
                modifier = Modifier.padding(bottom = 32.dp, top = 16.dp),
                textButton = stringResource(id = R.string.recover_password_register)
            )
        }
    }
}

@Preview
@Composable
fun PreviewRecoverPasswordContent() {
    CashDailyTheme {
        RecoverPasswordScreen(
            uiState = RecoverPasswordUiState(),
            emailValue = "",
            onEmailValueChange = {},
            onNavigationUp = {},
            onNavigationRegister = {},
            onSendEmailClick = {},
            onDismissDialog = {}
        )
    }
}