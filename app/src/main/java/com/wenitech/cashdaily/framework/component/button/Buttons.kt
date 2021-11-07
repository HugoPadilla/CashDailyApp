package com.wenitech.cashdaily.framework.component.commons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun PrimaryButtonExtended(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    @DrawableRes iconDrawable: Int? = null,
    shape: Dp? = null
) {

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.secondary
        ),
        shape = if (shape != null) {
            RoundedCornerShape(shape)
        } else MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (iconDrawable != null) {
                Icon(
                    modifier = Modifier.padding(end = 16.dp),
                    painter = painterResource(id = iconDrawable),
                    contentDescription = null,
                )
            }

            Text(
                style = MaterialTheme.typography.button,
                text = text,
            )
        }
    }

}

@Composable
fun TextButtonRegister(
    onNavigationRegisterListener: () -> Unit,
    modifier: Modifier = Modifier,
    textButton: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            style = MaterialTheme.typography.subtitle1,
            text = "¿No tiene una cuenta?"
        )

        TextButton(onClick = { onNavigationRegisterListener() }) {
            Text(text = textButton)
        }
    }
}

@Preview
@Composable
fun PreviewPrimaryButtonExtended() {
    CashDailyTheme {
        PrimaryButtonExtended(text = "PRIMARY BUTTON", onClick = {
            // Todo: Preview on click button
        })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextButtonRegister() {
    CashDailyTheme {
        TextButtonRegister(onNavigationRegisterListener = {}, textButton = "Registrate")
    }
}