package com.wenitech.cashdaily.framework.features.client.customerCredit

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.framework.DataExample.clientsData
import com.wenitech.cashdaily.framework.DataExample.creditData
import com.wenitech.cashdaily.framework.DataExample.quotesListData
import com.wenitech.cashdaily.framework.component.card.CardViewInfoCredit
import com.wenitech.cashdaily.framework.component.commons.PrimaryButtonExtended
import com.wenitech.cashdaily.framework.component.edittext.CustomTextField
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import com.wenitech.cashdaily.framework.ui.theme.success
import java.text.NumberFormat

@ExperimentalMaterialApi
@Composable
fun CustomerCreditScreen(
    idClient: String?,
    state: CustomerCreditState,
    onNewQuota: (value: String) -> Unit
) {

    val (showDialog, onShowDialog) = remember {
        mutableStateOf(false)
    }

    val (value, onValueChange) = remember {
        mutableStateOf("")
    }

    if (showDialog) {

        Dialog(onDismissRequest = { onShowDialog(false) }) {
            Card(elevation = 8.dp, shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Agregar cuota")
                    Spacer(modifier = Modifier.height(10.dp))

                    CustomTextField(
                        value = value,
                        onValueChange = onValueChange,
                        label = "Valor",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    PrimaryButtonExtended(
                        onClick = {
                            onNewQuota(value)
                            onShowDialog(false)
                        },
                        text = "Agregar cuota"
                    )
                }
            }
        }

    }

    CashDailyTheme {
        CustomerCreditContent(
            loading = state.loading,
            clientModel = state.client,
            creditModel = state.credit,
            listQuotaModel = state.listQuota
        ) {
            // Todo: Open dialog for new quota
            Log.d("ONTAP", idClient.toString())
            onShowDialog(true)

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
        }
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            if (!loading) {
                CardViewInfoCredit(elevation = 10.dp, client = clientModel, credit = creditModel)
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
