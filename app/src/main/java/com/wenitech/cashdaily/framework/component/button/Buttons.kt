package com.wenitech.cashdaily.framework.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import java.util.*

@Composable
fun PrimaryButtonExtended(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    enabled: Boolean = true,
    isUpperCase: Boolean = true,
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    @DrawableRes iconDrawable: Int? = null,
    shape: Dp? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        shape = shape?.let { RoundedCornerShape(it) } ?: MaterialTheme.shapes.small,
        colors = colors
    ) {
        Row(
            modifier = Modifier.padding(paddingValues = contentPaddingValues),
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
                text = if (isUpperCase) text.uppercase(Locale.getDefault()) else text,
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