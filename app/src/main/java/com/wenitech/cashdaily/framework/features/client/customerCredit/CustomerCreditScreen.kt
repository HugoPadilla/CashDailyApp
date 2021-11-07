package com.wenitech.cashdaily.framework.features.client.customerCredit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.framework.DataExample.clientsData
import com.wenitech.cashdaily.framework.DataExample.creditData
import com.wenitech.cashdaily.framework.DataExample.quotesListData
import com.wenitech.cashdaily.framework.component.commons.CreditClient
import com.wenitech.cashdaily.framework.features.client.customerCredit.viewModel.CustomerCreditViewModel
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import com.wenitech.cashdaily.framework.ui.theme.success
import java.text.NumberFormat

@ExperimentalMaterialApi
@Composable
fun CustomerCreditScreen(viewModel: CustomerCreditViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val isLoading by viewModel.loading.collectAsState()
    val client by viewModel.idClient.observeAsState()
    val customerClient by viewModel.customerState.collectAsState()
    val listQuota by viewModel.quotaModelCustomer.collectAsState()

    CashDailyTheme {
        CustomerCreditContent(
            loading = isLoading,
            clientModel = clientsData[0],
            creditModel = customerClient,
            listQuotaModel = listQuota
        ) {
            // Open dialog for new quota
            viewModel.setNewQuota(0.0, client!!, customerClient.id!!)
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun CustomerCreditContent(
    loading: Boolean = false,
    clientModel: Client,
    creditModel: Credit,
    listQuotaModel: List<Quota>,
    onAddQuota: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddQuota() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Button add new Client"
                )
            }
        }) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            if (!loading) {
                CreditClient(
                    idClient = clientModel.id ?: "",
                    name = clientModel.fullName,
                    direction = "${clientModel.city}, ${clientModel.direction}",
                    prestamo = creditModel.creditTotal,
                    deuda = creditModel.creditDebt,
                    cuota = creditModel.creditQuotaValue,
                    numeroCuotas = creditModel.amountFees,
                    numeroCuotasAdd = 0.0,
                    progress = 0.0f,
                    modifier = Modifier.padding(top = 20.dp, bottom = 18.dp)
                )

                QuotasList(listQuotaModel = listQuotaModel)
            } else {
                Text(text = "Cargando informacion...")
            }
        }
    }
}

@Composable
fun QuotasList(listQuotaModel: List<Quota>) {
    LazyColumn {

        item {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                style = MaterialTheme.typography.h6,
                text = "Movimientos recientes"
            )
        }

        items(listQuotaModel) { quota ->

            val date = quota.timestamp ?: "Fecha:"
            val currentAmount = NumberFormat.getCurrencyInstance().format(quota.value)

            Card(modifier = Modifier.padding(bottom = 4.dp), contentColor = success) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_trending_up),
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                        contentDescription = null
                    )
                    Text(
                        text = date.toString(),
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = 24.dp, end = 8.dp),
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1
                    )
                    Text(text = currentAmount, style = MaterialTheme.typography.subtitle2)
                }
            }

        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewCustomerCredit() {
    CashDailyTheme {
        CustomerCreditContent(
            clientModel = clientsData[0],
            creditModel = creditData[0],
            listQuotaModel = quotesListData
        ) {

        }
    }
}
