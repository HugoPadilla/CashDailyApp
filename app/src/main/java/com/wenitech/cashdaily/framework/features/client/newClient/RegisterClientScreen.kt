package com.wenitech.cashdaily.framework.features.client.newClient

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.component.button.IconButtonCustom
import com.wenitech.cashdaily.framework.component.button.PrimaryButtonExtended
import com.wenitech.cashdaily.framework.component.edittext.CustomTextField
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun RegisterClientScreen(
    uiState: RegisterClientUiState,
    onNavigationUp: () -> Unit,
    onNavigationToRegisterCredit: (String) -> Unit,
    onDismissDialog: () -> Unit,
    onFullNameChange: (String) -> Unit,
    onIdNumberChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onDirectionChange: (String) -> Unit,
    onRegisterClient: () -> Unit
) {

    when (uiState.showDialog) {
        TypeDialog.Loading -> {
            Dialog(onDismissRequest = { }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.subtitle_loading_add_client))
                    LinearProgressIndicator()
                }
            }
        }
        TypeDialog.Success -> {
            AlertDialog(
                onDismissRequest = { onDismissDialog() },
                title = { Text(text = "Cliente guardado") },
                text = { Text(text = "Este cliente se ha guardado.\n¿Desear hacerle un nuevo prestamo?") },
                confirmButton = {
                    OutlinedButton(onClick = { onNavigationToRegisterCredit(uiState.registeredCustomerId) }) {
                        Text(text = stringResource(R.string.accept))
                    }
                }
            )
        }
        TypeDialog.Error -> {
            AlertDialog(
                onDismissRequest = { onDismissDialog() },
                confirmButton = {
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Text(text = stringResource(R.string.accept))
                    }
                }
            )
        }
        TypeDialog.None -> {

        }
    }

    val focus = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Unspecified,
                contentPadding = PaddingValues(16.dp),
                elevation = 0.dp
            ) {
                IconButtonCustom(iconDrawable = R.drawable.ic_arrow_left) {
                    onNavigationUp()
                }
                Text(
                    text = stringResource(R.string.new_client),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 32.dp),
                    color = MaterialTheme.colors.onSurface
                )
            }
        },
        floatingActionButton = {
            PrimaryButtonExtended(
                text = stringResource(R.string.save_client),
                onClick = onRegisterClient,
                iconDrawable = R.drawable.ic_user_plus,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                enabled = uiState.buttonEnabled
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = stringResource(id = R.string.title_new_client),
                modifier = Modifier.padding(top = 18.dp, bottom = 18.dp),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight(600)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = stringResource(R.string.information_people),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground.copy(alpha = .5f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                label = stringResource(R.string.full_name),
                value = uiState.fullName,
                onValueChange = onFullNameChange,
                leadingIcon = {
                    IconButtonCustom(iconDrawable = R.drawable.ic_user) {
                        // Todo Icon button click
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                CustomTextField(
                    label = stringResource(R.string.identity),
                    value = uiState.idNumber,
                    onValueChange = onIdNumberChange,
                    modifier = Modifier.weight(.5f),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Right) })
                )
                Spacer(modifier = Modifier.width(10.dp))
                CustomTextField(
                    label = stringResource(R.string.gender),
                    value = uiState.gender,
                    onValueChange = onGenderChange,
                    modifier = Modifier.weight(.5f),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) })
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.information_contact),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground.copy(alpha = .5f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                label = stringResource(R.string.phone),
                value = uiState.phoneNumber,
                onValueChange = onPhoneNumberChange,
                leadingIcon = {
                    IconButtonCustom(iconDrawable = R.drawable.ic_phone) {
                        // Todo Icon button click
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                label = stringResource(R.string.city),
                value = uiState.city,
                onValueChange = onCityChange,
                leadingIcon = {
                    IconButtonCustom(iconDrawable = R.drawable.ic_map) {
                        // Todo Icon button click
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                value = uiState.direction,
                onValueChange = onDirectionChange,
                label = stringResource(R.string.direction),
                leadingIcon = {
                    IconButtonCustom(iconDrawable = R.drawable.ic_map_pin) {
                        // Todo Icon button click
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { onRegisterClient() })
            )

            Spacer(modifier = Modifier.height(84.dp))
        }
    }
}

@Composable
@Preview
fun PreviewRegisterClientContent() {
    CashDailyTheme {
        RegisterClientScreen(
            uiState = RegisterClientUiState(),
            onNavigationUp = {},
            onNavigationToRegisterCredit = {},
            onDismissDialog = {},
            onFullNameChange = {},
            onIdNumberChange = {},
            onGenderChange = {},
            onPhoneNumberChange = {},
            onCityChange = {},
            onDirectionChange = {},
            onRegisterClient = {}
        )
    }
}