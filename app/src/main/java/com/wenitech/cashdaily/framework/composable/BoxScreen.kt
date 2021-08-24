package com.wenitech.cashdaily.framework.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.framework.ScaffoldScreen
import com.wenitech.cashdaily.framework.commons.cashTransactionsData
import com.wenitech.cashdaily.framework.composable.commons.CashAvailableCardView
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import com.wenitech.cashdaily.framework.ui.theme.success
import java.text.NumberFormat

@Composable
fun BoxScreen(
    navController: NavController,
    cashAvailable: Double,
    transactionsList: List<CashTransactions>,
    onAddMonetClick: () -> Unit,
    onRemoveClick: () -> Unit
) {

    ScaffoldScreen(navController = navController) {
        BoxContent(
            cashAvailable = cashAvailable,
            cashTransactions = transactionsList,
            onAddMonetClick = onAddMonetClick,
            onRemoveClick = onRemoveClick
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxContent(
    cashAvailable: Double,
    cashTransactions: List<CashTransactions>,
    onAddMonetClick: () -> Unit,
    onRemoveClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        LazyColumn() {

            item {
                CashAvailableCardView(
                    money = cashAvailable,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        modifier = Modifier,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.secondary),
                        onClick = onRemoveClick
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 8.dp),
                            painter = painterResource(id = R.drawable.ic_trending_down),
                            contentDescription = null
                        )
                        Text(text = "Remove money")
                    }
                    OutlinedButton(
                        contentPadding = PaddingValues(horizontal = 30.dp, vertical = 8.dp),
                        modifier = Modifier,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = success),
                        onClick = onAddMonetClick
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 8.dp),
                            painter = painterResource(id = R.drawable.ic_trending_up),
                            contentDescription = null
                        )
                        Text(text = "Add money")
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    style = MaterialTheme.typography.h6,
                    text = "Movimiento recientes"
                )
            }

            items(cashTransactions) { transition ->

                val date = transition.serverTimestamp?.toDate() ?: "Fecha:"
                val currentAmount = NumberFormat.getCurrencyInstance().format(transition.value)

                Card(
                    modifier = Modifier.padding(bottom = 4.dp),
                    contentColor = success
                ) {
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

}

@Preview(showBackground = true)
@Composable
fun PreviewBoxContent() {
    CashDailyTheme {
        BoxContent(
            cashAvailable = 12000.00,
            cashTransactions = cashTransactionsData,
            onAddMonetClick = {},
            onRemoveClick = {})
    }
}