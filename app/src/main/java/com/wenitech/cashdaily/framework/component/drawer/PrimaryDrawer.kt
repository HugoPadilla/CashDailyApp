package com.wenitech.cashdaily.framework.component.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun PrimaryDrawer(onCloseSession: () -> Unit) {
    Column {
        Text(text = "First action")
        Text(text = "Second action")
        Button(onClick = onCloseSession) {
            Text(text = "Cerrar sesion")
        }
    }
}