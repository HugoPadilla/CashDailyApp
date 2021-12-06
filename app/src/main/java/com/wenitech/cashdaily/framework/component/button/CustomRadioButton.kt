package com.wenitech.cashdaily.framework.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun CustomRadioButton(
    selected: Boolean,
    onOptionSelected: () -> Unit,
    text: String
) {
    Surface(
        modifier = Modifier.selectable(
            selected = selected,
            onClick = onOptionSelected
        ),
        shape = RoundedCornerShape(5.dp),
        elevation = if (selected) 5.dp else 0.dp,
        color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        contentColor = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
        border = BorderStroke(2.dp, color = MaterialTheme.colors.primary.copy(.4f))
    ) {
        Row(modifier = Modifier.padding(18.dp, 12.dp)) {
            Text(text = text, style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemSelected() {
    CashDailyTheme {
        Column {
            CustomRadioButton(selected = false, onOptionSelected = {}, text = "Option 1")
            Spacer(modifier = Modifier.height(16.dp))
            CustomRadioButton(selected = true, onOptionSelected = {}, text = "Option 1")
        }
    }
}