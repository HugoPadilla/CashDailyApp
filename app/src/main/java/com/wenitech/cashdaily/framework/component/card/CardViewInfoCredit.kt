package com.wenitech.cashdaily.framework.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.framework.DataExample.clientsData
import com.wenitech.cashdaily.framework.DataExample.creditData
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import java.text.NumberFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardViewInfoCredit(
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke? = null,
    elevation: Dp = 1.dp,
    client: Client,
    credit: Credit
) {
    Card(
        modifier = modifier,
        border = borderStroke,
        elevation = elevation,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column {

            ClientItem(clientModel = client, onClick = { idClient, refCredit -> })

            Divider()

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Row(
                    modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Column {
                        Text(style = MaterialTheme.typography.subtitle1, text = "Deuda")
                        Text(
                            style = MaterialTheme.typography.h6,
                            text = NumberFormat.getCurrencyInstance().format(credit.creditDebt)
                        )
                    }
                    Column {
                        Text(style = MaterialTheme.typography.subtitle1, text = "Prestamo")
                        Text(
                            style = MaterialTheme.typography.h6,
                            text = NumberFormat.getCurrencyInstance().format(credit.creditTotal)
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            style = MaterialTheme.typography.caption,
                            text = "Cuota: ${
                                NumberFormat.getCurrencyInstance().format(credit.creditQuotaValue)
                            }"
                        )
                        Text(
                            style = MaterialTheme.typography.overline,
                            text = "${((credit.creditTotal.toFloat() - credit.creditDebt.toFloat()) * 100f) / credit.creditTotal.toFloat()}%"
                        )
                    }

                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(),
                        progress = (((credit.creditTotal.toFloat() - credit.creditDebt.toFloat()) * 100f) / credit.creditTotal.toFloat()) / 100f
                    )
                }

            }

        }
    }
}

@Preview
@Composable
fun PreviewCreditClient() {
    CashDailyTheme {
        CardViewInfoCredit(
            client = clientsData[3],
            credit = creditData[0]
        )
    }
}