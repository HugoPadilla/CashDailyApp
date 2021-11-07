package com.wenitech.cashdaily.framework.features.credit.newCredit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.component.edittext.CustomTextField
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

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Total credito",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "$200,000",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                label = "Valor prestamo",
                value = "250,000",
                icon = R.drawable.ic_dollar,
                onValueChange = {},
                modifier = Modifier.weight(.6f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            CustomTextField(
                label = "Porcentaje",
                value = "90",
                icon = R.drawable.ic_percent,
                onValueChange = {},
                modifier = Modifier.weight(.3f)
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