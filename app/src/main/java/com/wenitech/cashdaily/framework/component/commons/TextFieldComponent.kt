package com.wenitech.cashdaily.framework.component.commons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    messageError: String? = null,
    @DrawableRes icon: Int,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = label) },
            value = value,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                )
            },
            isError = !messageError.isNullOrBlank(),
            singleLine = singleLine
        )
        messageError?.let { message ->
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 2.dp),
                text = message,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.error
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextField() {
    CashDailyTheme {
        CustomTextField(
            modifier = Modifier.padding(16.dp),
            label = "Custon text",
            value = "",
            icon = R.drawable.ic_mail,
            onValueChange = {},
            messageError = "Message de error"
        )
    }
}