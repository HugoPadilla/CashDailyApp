package com.wenitech.cashdaily.framework

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wenitech.cashdaily.framework.composable.*
import com.wenitech.cashdaily.framework.features.caja.viewModel.BoxViewModel
import com.wenitech.cashdaily.framework.features.client.listClient.viewModel.ClientViewModel
import com.wenitech.cashdaily.framework.features.home.viewModel.HomeFragmentViewModel
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@ExperimentalMaterialApi
@Composable
fun NavComposeApp() {

    val navController = rememberNavController()

    CashDailyTheme {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {

            // Bottom nav
            composable(IconScreens.Home.route) {
                val viewModel = hiltViewModel<HomeFragmentViewModel>()
                HomeScreen(navController = navController, viewModel = viewModel) {
                    // Navigate to new client screen
                    navController.navigate("register_credit")
                }
            }

            composable(IconScreens.Clients.route) {
                val viewModel = hiltViewModel<ClientViewModel>()
                ClientsComposeScreen(
                    navController = navController,
                    viewModel = viewModel,
                    onFloatingButtonClick = { /*TODO*/ },
                    onClientClick = { idClient, refCredit ->
                        // Navigate to client details
                    }
                )
            }

            composable(IconScreens.Caja.route) {

                val viewModel = hiltViewModel<BoxViewModel>()

                val boxState by viewModel.boxState.collectAsState()
                val transactionsList by viewModel.cashMovement.collectAsState()

                BoxScreen(
                    navController = navController,
                    cashAvailable = boxState.totalCash,
                    transactionsList = transactionsList,
                    onValueClick = { value, description ->
                        viewModel.addMoneyOnBox(value, description)
                    }
                )
            }

            composable(IconScreens.Informe.route) {
                InformeScreen(navController = navController)
            }

            composable(route = "register_credit") {
                RegisterCreditScreen()
            }

        }
    }
}