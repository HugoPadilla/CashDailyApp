package com.wenitech.cashdaily.framework.features.client.profileClient

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun ProfileClientScreen() {
    ProfileClientContent()
}

@Composable
fun ProfileClientContent() {
    // Todo: Maquetar pantalla
    Scaffold {
        Text(text = "Profile client")
    }
}

@Preview
@Composable
fun PreviewProfileClientScreen() {
    CashDailyTheme {
        ProfileClientContent()
    }
}