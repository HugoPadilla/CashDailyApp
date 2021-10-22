package com.wenitech.cashdaily.framework

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wenitech.cashdaily.framework.component.*
import com.wenitech.cashdaily.framework.features.caja.viewModel.BoxViewModel
import com.wenitech.cashdaily.framework.features.client.listClient.viewModel.ClientViewModel
import com.wenitech.cashdaily.framework.features.home.viewModel.HomeFragmentViewModel
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import com.wenitech.cashdaily.framework.utils.IconScreens

@ExperimentalMaterialApi
@Composable
fun NavComposeApp(modifier: Modifier, navController: NavHostController, onNavigationLogin: () -> Unit) {

    CashDailyTheme {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = IconScreens.Home.route
        ) {

            // Bottom nav
            composable(IconScreens.Home.route) {
                val viewModel = hiltViewModel<HomeFragmentViewModel>()
                HomeScreen(navController = navController, viewModel = viewModel) {
                    // Navigate to new client screen
                    //navController.navigate("login")
                    onNavigationLogin()
                }
            }

            composable(IconScreens.Clients.route) {
                val viewModel = hiltViewModel<ClientViewModel>()
                ClientsComposeScreen(
                    navController = navController,
                    viewModel = viewModel,
                    onNewClientFloatingButtonClick = { /*TODO*/ },
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