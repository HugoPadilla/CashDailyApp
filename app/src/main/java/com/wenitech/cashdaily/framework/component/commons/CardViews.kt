package com.wenitech.cashdaily.framework.component.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.framework.ui.theme.Shapes
import java.text.NumberFormat

@Composable
fun CashAvailableCardView(money: Double, modifier: Modifier = Modifier, elevation: Dp = 16.dp) {

    val textMoney = NumberFormat.getCurrencyInstance().format(money)

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = elevation,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Column(modifier = Modifier.padding(horizontal = 26.dp, vertical = 32.dp)) {
            Text(
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSecondary,
                text = "Efectivo disponible"
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = MaterialTheme.typography.h4.fontSize
                ),
                color = MaterialTheme.colors.onSecondary,
                text = textMoney
            )
        }
    }

}

@ExperimentalMaterialApi
@Composable
fun CreditClient(
    idClient: String,
    name: String,
    direction: String,
    prestamo: Double,
    deuda: Double,
    cuota: Double,
    numeroCuotas: Int,
    numeroCuotasAdd: Double,
    progress: Float,
    modifier: Modifier = Modifier,
    elevation: Dp = 16.dp
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = elevation,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 2.dp, end = 20.dp, bottom = 18.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            ClientItem(
                clientModel = Client(id = idClient, fullName = name, direction = direction),
                onClick = { idClient, refCredit ->

                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(2.dp)
                    .background(
                        color = MaterialTheme.colors.background
                    )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                ItemValue(title = "Prestamo", value = prestamo)
                ItemValue(title = "Deuda", value = deuda)
                ItemValue(title = "Cuota", value = cuota)
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                Text(
                    text = "$numeroCuotasAdd/$numeroCuotas",
                    style = MaterialTheme.typography.overline
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(Shapes.medium),
                    color = MaterialTheme.colors.secondary,
                    progress = progress
                )
            }
        }
    }
}

@Composable
fun ItemValue(title: String, value: Double) {
    val valueFormat = NumberFormat.getCurrencyInstance().format(value)
    Column {
        Text(text = title, style = MaterialTheme.typography.caption)
        Text(text = valueFormat, style = MaterialTheme.typography.subtitle2)
    }
}
