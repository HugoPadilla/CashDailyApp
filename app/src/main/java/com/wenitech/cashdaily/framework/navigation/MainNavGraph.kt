package com.wenitech.cashdaily.framework.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.wenitech.cashdaily.framework.features.client.newClient.RegisterClientScreen
import com.wenitech.cashdaily.framework.features.client.newClient.RegisterClientViewModel
import com.wenitech.cashdaily.framework.features.credit.registerCredit.RegisterCreditScreen
import com.wenitech.cashdaily.framework.features.credit.registerCredit.RegisterCreditViewModel
import com.wenitech.cashdaily.framework.features.home.HomeScreen
import com.wenitech.cashdaily.framework.features.home.viewModel.HomeViewModel
import com.wenitech.cashdaily.framework.features.informe.InformeScreen
import com.wenitech.cashdaily.framework.navigation.nestedNavGraph.customerCreditNavGraph
import com.wenitech.cashdaily.framework.navigation.nestedNavGraph.registerCreditNavGraph

@ExperimentalMaterialApi
@Composable
fun MainNavGraph(
    modifier: Modifier,
    navController: NavHostController
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BottomNavDestinations.Home.route
    ) {

        // Bottom nav bar
        composable(BottomNavDestinations.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
                onNewCreditClick = { idClient ->
                    navController.navigate(CREDIT_ROUTE)
                }
            )
        }

        composable(BottomNavDestinations.Clients.route) {
            val viewModel = hiltViewModel<ClientViewModel>()
            val state = viewModel.uiStates.collectAsState()
            ClientsComposeScreen(
                state = state.value,
                onNavigateNewClient = { navController.navigate("register_client") },
                onNavigateClientInfo = { idClient, refCredit ->
                    navController.navigate(
                        route = ClientDestinations.CustomerCredit.route + "?idClient=$idClient" + "?refCredit=$refCredit"
                    )
                }
            )
        }

        composable(BottomNavDestinations.Box.route) {

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

        composable(BottomNavDestinations.Report.route) {
            InformeScreen(navController = navController)
        }

        // Nested nav graph
        customerCreditNavGraph(navController = navController)

        registerCreditNavGraph(navController)

    }

}
