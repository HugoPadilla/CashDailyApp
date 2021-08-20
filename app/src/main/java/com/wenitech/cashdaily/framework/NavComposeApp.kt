package com.wenitech.cashdaily.framework

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun NavComposeApp() {
    val navController = rememberNavController()

    CashDailyTheme {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {

            }
        }
    }
}