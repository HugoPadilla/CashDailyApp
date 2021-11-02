package com.wenitech.cashdaily.framework.features.credit.newCredit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.component.commons.CustomTextField
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun RegisterCreditScreen() {

}

@Composable
fun RegisterCreditContent() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = "Total credito",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "$200,000.00",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.fillMaxWidth()
        )

        Row {
            CustomTextField(
                label = "Valor prestamo",
                value = "",
                icon = R.drawable.ic_dollar,
                onValueChange = {}
            )

            CustomTextField(
                label = "Porcentaje",
                value = "",
                icon = R.drawable.ic_percent,
                onValueChange = {}
            )
        }

        Row {
            OutlinedTextField(
                label = { Text(text = "Valor prestamo") },
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(0.5f)
            )

            OutlinedTextField(
                label = { Text(text = "Valor prestamo") },
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(0.5f)
            )
        }

    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewComposable() {
    CashDailyTheme {
        RegisterCreditContent()
    }
}