package com.wenitech.cashdaily.framework.features.credit.newCredit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.component.commons.PrimaryButtonExtended
import com.wenitech.cashdaily.framework.component.edittext.CustomTextField
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun RegisterCreditScreen(viewModel: RegisterCreditViewModel = hiltViewModel()) {

    val totalCredit by viewModel.totalCredit.collectAsState()
    val dateCredit by viewModel.dateCredit.collectAsState()
    val valueCredit by viewModel.valueCredit.collectAsState()
    val percentCredit by viewModel.percentCredit.collectAsState()

    val modalityOptions by viewModel.modalityOptions.collectAsState()
    val modalitySelected by viewModel.modalitySelected.collectAsState()

    val amountFees by viewModel.amountFees.collectAsState()
    val quotaValue by viewModel.quotaValue.collectAsState()


    RegisterCreditContent(
        totalCredit = "$${totalCredit}",
        dateCredit = dateCredit,
        setDateCredit = {  },
        valueCredit = valueCredit,
        setValueCredit = {  },
        percentCredit = percentCredit,
        setPercentCredit = {  },
        modalidadOptions = modalityOptions,
        onOptionSelected = viewModel::onModalityOptionSelected,
        selectedOption = modalitySelected,
        plazoCredit = amountFees,
        setPlazoCredit = {  },
        valueQuota = quotaValue,
        setValueQuotaCredit = {  },
        onSaveButtonClick = viewModel::saveCustomerCreditFromClient
    )

}

@Composable
fun RegisterCreditContent(
    totalCredit: String,
    dateCredit: String,
    setDateCredit: (date: String) -> Unit,
    valueCredit: String,
    setValueCredit: (valueCredi: String) -> Unit,
    percentCredit: String,
    setPercentCredit: (String) -> Unit,
    modalidadOptions: List<String>,
    onOptionSelected: (String) -> Unit,
    selectedOption: String,
    plazoCredit: String,
    setPlazoCredit: (String) -> Unit,
    valueQuota: String,
    setValueQuotaCredit: (String) -> Unit,
    onSaveButtonClick: () -> Unit
) {

    Scaffold {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Deuda total",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary.copy(.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = totalCredit,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(30.dp))

            CustomTextField(
                label = "Fecha de credito",
                value = dateCredit,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_down),
                        contentDescription = null
                    )
                },
                onValueChange = setDateCredit
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CustomTextField(
                    label = "Valor prestamo",
                    value = valueCredit,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dollar),
                            contentDescription = null
                        )
                    },
                    onValueChange = setValueCredit,
                    modifier = Modifier.weight(.6f)
                )

                CustomTextField(
                    label = "Porcentaje",
                    value = percentCredit,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_percent),
                            contentDescription = null
                        )
                    },
                    onValueChange = setPercentCredit,
                    modifier = Modifier.weight(.4f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Forma de cobro",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                modalidadOptions.forEach { text ->
                    CustomRadioButton(
                        selected = (text == selectedOption),
                        onOptionSelected = onOptionSelected,
                        text = text
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
                    label = "Dias de plazo",
                    value = plazoCredit,
                    leadingIcon = null,
                    trailingIcon = null,
                    onValueChange = setPlazoCredit
                )

                CustomTextField(
                    modifier = Modifier.weight(5f),
                    label = "Valor cuota",
                    value = valueQuota,
                    onValueChange = setValueQuotaCredit
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButtonExtended(
                onClick = onSaveButtonClick,
                text = "Guardar prestamo"
            )
        }
    }
}

@Composable
fun CustomRadioButton(
    selected: Boolean,
    onOptionSelected: (String) -> Unit,
    text: String
) {
    Surface(
        modifier = Modifier.selectable(
            selected = selected,
            onClick = { onOptionSelected(text) }
        ),
        shape = RoundedCornerShape(5.dp),
        elevation = if (selected) 5.dp else 0.dp,
        color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        contentColor = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
        border = BorderStroke(2.dp, color = MaterialTheme.colors.primary.copy(.4f))
    ) {
        Row(modifier = Modifier.padding(18.dp, 12.dp)) {
            Text(text = text, style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemSelected() {
    CashDailyTheme {
        Column {
            CustomRadioButton(selected = false, onOptionSelected = {}, text = "Option 1")
            Spacer(modifier = Modifier.height(16.dp))
            CustomRadioButton(selected = true, onOptionSelected = {}, text = "Option 1")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewComposable() {
    CashDailyTheme {
        RegisterCreditContent(
            totalCredit = "$100,000.00",
            dateCredit = "",
            setDateCredit = {},
            valueCredit = "",
            setValueCredit = {},
            percentCredit = "",
            setPercentCredit = {},
            modalidadOptions = listOf("Diario", "Semanal", "Quincenal", "Mensual"),
            onOptionSelected = {},
            selectedOption = "Diario",
            plazoCredit = "",
            setPlazoCredit = {},
            valueQuota = "",
            setValueQuotaCredit = {},
            onSaveButtonClick = { /* Todo on click */}
        )
    }
}
