package com.wenitech.cashdaily.framework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavComposeApp()
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun NavComposeAppPreview() {
    CashDailyTheme {
        NavComposeApp()
    }
}