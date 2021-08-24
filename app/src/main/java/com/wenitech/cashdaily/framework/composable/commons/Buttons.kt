package com.wenitech.cashdaily.framework.composable.commons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun PrimaryExtendeButton(
    modifier: Modifier = Modifier,
    text: String,
    iconDrawable: Int = R.drawable.ic_user_plus,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick ,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.secondary
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                painter = painterResource(id = iconDrawable),
                contentDescription = null,
            )

            Text(
                style = MaterialTheme.typography.button,
                text = text,
            )
        }
    }

}

@Preview
@Composable
fun PreviewPrimaryNButton() {
    CashDailyTheme {
        PrimaryExtendeButton(text = "PRIMARY BUTTON", onClick = {
            // Todo On click in button
        })
    }
}