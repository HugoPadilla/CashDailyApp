package com.wenitech.cashdaily.framework.component.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ShowAlertDialog(
    showDialog: Boolean,
    title: String,
    text: String,
    textPositive: String? = null,
    textNegative: String? = null,
    onDismissRequest: () -> Unit,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit
) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = title) },
            text = { Text(text = text) },
            confirmButton = {
                if (!textPositive.isNullOrBlank()) {
                    TextButton(onClick = { onPositiveClick() }) {
                        Text(text = textPositive)
                    }
                }
            },
            dismissButton = {
                if (!textNegative.isNullOrBlank()) {
                    TextButton(onClick = { onNegativeClick() }) {
                        Text(text = textNegative)
                    }
                }
            },
        )
    }
}