package com.wenitech.cashdaily.framework.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import kotlinx.coroutines.launch
import java.text.NumberFormat

@ExperimentalMaterialApi
@Composable
fun BoxScreen(
    navController: NavController,
    cashAvailable: Double,
    transactionsList: List<CashTransactions>,
    onValueClick: (Double, String) -> Unit,
) {

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    var label by remember { mutableStateOf("") }
    var textButton by remember { mutableStateOf("") }
    var isRemoveMoney by remember { mutableStateOf(false) }


    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            ModalBottomSheetContent(
                textButton = label,
                valueLabel = textButton,
                onValueClick = { value, description ->
                    scope.launch { if (bottomSheetState.isVisible) bottomSheetState.hide() }
                    onValueClick(value, description)
                },
                isRemoveMoney = isRemoveMoney
            )
        }) {
        ScaffoldScreen(navController = navController) {
            BoxContent(
                cashAvailable = cashAvailable,
                cashTransactions = transactionsList,
                onAddMonetClick = {
                    scope.launch {
                        label = "Escribe un valor"
                        textButton = "Agregar dinero"
                        isRemoveMoney = false
                        bottomSheetState.show()
                    }
                },
                onRemoveClick = {
                    scope.launch {
                        label = "Escribe un valor"
                        textButton = "Registrar gasto"
                        isRemoveMoney = true
                        bottomSheetState.show()
                    }
                }
            )
        }
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

@Composable
fun ModalBottomSheetContent(
    textButton: String,
    valueLabel: String,
    descriptionLabel: String = "Descripcion",
    isRemoveMoney: Boolean,
    onValueClick: (Double, String) -> Unit
) {

    var value by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val isValid by derivedStateOf { value.isNotBlank() }

    Column(
        modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { value = it },
            label = { Text(text = valueLabel) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .heightIn(100.dp, 140.dp),
            value = description,
            onValueChange = { description = it },
            label = { Text(text = descriptionLabel) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Send
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (isRemoveMoney) {
                    onValueClick(value.toDouble() * -1, description.trim())
                } else {
                    onValueClick(value.toDouble(), description.trim())
                }

                value = ""

            }, enabled = isValid
        ) {
            Text(text = textButton)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottonSheetContent() {
    ModalBottomSheetContent(
        textButton = "Agregar dinero",
        valueLabel = "Escribe un valor",
        onValueClick = { _, _ -> },
        isRemoveMoney = false
    )
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