package com.wenitech.cashdaily.framework.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import java.text.NumberFormat

@Composable
fun CardViewInfoBox(
    money: Double,
    modifier: Modifier = Modifier,
    elevation: Dp = 16.dp
) {

    val textMoney = NumberFormat.getCurrencyInstance().format(money)

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = elevation,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 26.dp, vertical = 32.dp)
        ) {
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

@Preview
@Composable
fun PreviewCardview() {
    CashDailyTheme {
        CardViewInfoBox(money = 350000.00)
    }
}
