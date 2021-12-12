package com.wenitech.cashdaily.framework.features.credit.registerCredit

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.component.button.CustomRadioButton
import com.wenitech.cashdaily.framework.component.button.IconButtonCustom
import com.wenitech.cashdaily.framework.component.button.PrimaryButton
import com.wenitech.cashdaily.framework.component.edittext.CustomTextField
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@ExperimentalMaterialApi
@Composable
fun RegisterCreditScreen(
    uiState: RegisterCreditUiState,
    onDismissDialog: () -> Unit,
    onSuccessButtonClick: () -> Unit,
    onDateCredit: (String) -> Unit,
    onCreditValue: (String) -> Unit,
    onCreditPercent: (String) -> Unit,
    onOptionSelected: (ModalityOptions) -> Unit,
    onAmountFees: (String) -> Unit,
    onCreditQuotaValue: (String) -> Unit,
    onBackNavigateClick: () -> Unit,
    onSaveButtonClick: () -> Unit
) {

    if (uiState.isSuccessResult) {
        AlertDialog(
            onDismissRequest = onDismissDialog,
            title = { Text(text = "Registro completo") },
            text = {
                Text(
                    text = "Este credito se ha guardado corectamente"
                )
            },
            confirmButton = {
                OutlinedButton(onClick = onSuccessButtonClick) {
                    Text(text = "Aceptar")
                }
            }
        )
    }

    if (uiState.isLoadingResult) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Registrando nuevo credito..",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(16.dp)
                )
                LinearProgressIndicator(modifier = Modifier.padding(horizontal = 42.dp))
            }
        }
    }

    if (uiState.isFailedResult) {
        AlertDialog(
            onDismissRequest = { onDismissDialog() },
            title = { Text(text = "Ocurrio un error") },
            text = { Text(text = "No fue posible agregar este credito, intenta mas tarde") },
            buttons = {
                Button(onClick = { onDismissDialog() }) {
                    Text(text = "Aceptar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButtonCustom(
                    iconDrawable = R.drawable.ic_arrow_left,
                    modifier = Modifier.padding(end = 28.dp)
                ) {
                    onBackNavigateClick()
                }

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(text = "Paso 2/2", style = MaterialTheme.typography.h6)
                        Text(
                            text = "Registrar credito",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = .7f)
                        )
                    }
                }

            }
        },
        floatingActionButton = {
            PrimaryButton(
                onClick = onSaveButtonClick,
                text = "Guardar prestamo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                iconDrawable = R.drawable.ic_file_plus,
                enabled = uiState.buttonEnable
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Valor total",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary.copy(.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = uiState.totalCredit,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Detalles del credito",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground.copy(alpha = .5f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                label = "Fecha de credito",
                value = uiState.dateCredit,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_down),
                        contentDescription = null
                    )
                },
                onValueChange = onDateCredit,
                readOnly = true,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CustomTextField(
                    label = "Valor prestamo",
                    value = uiState.creditValue,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dollar),
                            contentDescription = null
                        )
                    },
                    onValueChange = { value ->
                        onCreditValue(value.filter { it.isDigit() })
                    },
                    modifier = Modifier.weight(.6f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                CustomTextField(
                    label = "Porcentaje",
                    value = uiState.creditPercent,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_percent),
                            contentDescription = null
                        )
                    },
                    onValueChange = { value ->
                        if (value.length <= 3) onCreditPercent(value.filter { it.isDigit() })
                    },
                    modifier = Modifier.weight(.4f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Forma de cobro",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onBackground.copy(alpha = .5f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                uiState.modalityCreditOptions.forEach { modalityOption ->
                    CustomRadioButton(
                        selected = (modalityOption.modalityOption == uiState.modalitySelected),
                        onOptionSelected = { onOptionSelected(modalityOption.modalityOption) },
                        text = modalityOption.modalityLabel
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CustomTextField(
                    modifier = Modifier.weight(5f),
                    label = uiState.amountFestLabelText,
                    value = uiState.amountFest,
                    onValueChange = { value ->
                        if (value.length <= 3)
                            onAmountFees(value.filter { it.isDigit() })
                    },
                    leadingIcon = null,
                    trailingIcon = null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                CustomTextField(
                    modifier = Modifier.weight(5f),
                    label = "Valor cuota",
                    value = uiState.creditQuotaValue,
                    onValueChange = onCreditQuotaValue,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    readOnly = true
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

        }
    }
}

private fun CustomToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun PreviewComposable() {
    CashDailyTheme {
        RegisterCreditScreen(
            onDismissDialog = {},
            uiState = RegisterCreditUiState(
                modalityCreditOptions = listOf(
                    ModalityCredit.Diario,
                    ModalityCredit.Semanal,
                    ModalityCredit.Quincenal,
                    ModalityCredit.Mensual
                )
            ),
            onBackNavigateClick = {},
            onSuccessButtonClick = {},
            onDateCredit = {},
            onCreditValue = {},
            onCreditPercent = {},
            onOptionSelected = {},
            onAmountFees = {},
            onCreditQuotaValue = {},
            onSaveButtonClick = { /* Todo on click */ }
        )
    }
}
