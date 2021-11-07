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
import com.wenitech.cashdaily.framework.features.caja.BoxScreen
import com.wenitech.cashdaily.framework.features.caja.viewModel.BoxViewModel
import com.wenitech.cashdaily.framework.features.client.listClient.ClientsComposeScreen
import com.wenitech.cashdaily.framework.features.client.listClient.viewModel.ClientViewModel
import com.wenitech.cashdaily.framework.features.credit.newCredit.RegisterCreditScreen
import com.wenitech.cashdaily.framework.features.home.HomeScreen
import com.wenitech.cashdaily.framework.features.home.viewModel.HomeViewModel
import com.wenitech.cashdaily.framework.features.informe.InformeScreen
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import com.wenitech.cashdaily.framework.utils.MainNavRoutes

@ExperimentalMaterialApi
@Composable
fun NavComposeApp(
    modifier: Modifier,
    navController: NavHostController,
    onNavigationLogin: () -> Unit
) {

    CashDailyTheme {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = MainNavRoutes.Home.route
        ) {

            // Bottom nav
            composable(MainNavRoutes.Home.route) {
                val viewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(navController = navController, viewModel = viewModel) {
                    // Navigate to new client screen
                    //navController.navigate("login")
                    onNavigationLogin()
                }
            }

            composable(MainNavRoutes.Clients.route) {
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

            composable(MainNavRoutes.Caja.route) {

                val viewModel = hiltViewModel<BoxViewModel>()

                val boxState by viewModel.boxModelState.collectAsState()
                val transactionsList by viewModel.cashMovementModel.collectAsState()

                BoxScreen(
                    navController = navController,
                    cashAvailable = boxState.totalCash,
                    transactionsModelList = transactionsList,
                    onValueClick = { value, description ->
                        viewModel.addMoneyOnBox(value, description)
                    }
                )
            }

            composable(MainNavRoutes.Informe.route) {
                InformeScreen(navController = navController)
            }

            composable(route = "register_credit") {
                RegisterCreditScreen()
            }

        }
    }
}