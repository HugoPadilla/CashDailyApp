package com.wenitech.cashdaily.framework.composable.commons

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wenitech.cashdaily.framework.features.client.customerCredit.viewModel.CustomerCreditViewModel
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun CustomerCreditScreen(viewModel: CustomerCreditViewModel) {
    CustomerCreditContent()
}

@Composable
fun CustomerCreditContent() {
    CashDailyTheme {

        Scaffold() {

        }

    }
}

@Preview
@Composable
fun PreviewCustomerCredit(){
    CustomerCreditContent()
}