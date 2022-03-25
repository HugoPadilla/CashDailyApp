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
import com.wenitech.cashdaily.framework.features.authentication.navigation.AuthDestinationsRoot
import com.wenitech.cashdaily.framework.features.authentication.navigation.authenticationGraph
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
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MainGraph(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AuthDestinationsRoot
    ) {

        // Authentication graph
        authenticationGraph(navController = navController)

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

        composable(route = "register_client") {

            val viewModel: RegisterClientViewModel = hiltViewModel()

            RegisterClientScreen(
                uiState = viewModel.uiState.collectAsState().value,
                onNavigationUp = { navController.navigateUp() },
                onNavigationToRegisterCredit = {
                    navController.navigate(ClientDestinations.RegisterCredit.route + "/$it")
                },
                onDismissDialog = viewModel::onDismissDialog,
                onFullNameChange = viewModel::setFullName,
                onIdNumberChange = viewModel::setIdNumber,
                onGenderChange = viewModel::setGender,
                onPhoneNumberChange = viewModel::setPhoneNumber,
                onCityChange = viewModel::setCity,
                onDirectionChange = viewModel::setDirection,
                onRegisterClient = viewModel::saveNewClient
            )
        }

        composable(
            route = ClientDestinations.RegisterCredit.route + "/{idClient}"
        ) { navBackStackEntry ->

            val viewModel: RegisterCreditViewModel = hiltViewModel()

            val idClientArg = navBackStackEntry.arguments?.getString("idClient")
            LaunchedEffect(key1 = Unit, block = {
                idClientArg?.let { viewModel.setIdClient(it) }
            })

            val uiState by viewModel.uiState.collectAsState()

            RegisterCreditScreen(
                uiState = uiState,
                onDismissDialog = viewModel::onDismissDialog,
                onSuccessButtonClick = viewModel::onSuccessDialog,
                onDateCredit = viewModel::setDateCredit,
                onCreditValue = viewModel::setCreditValue,
                onCreditPercent = viewModel::setCreditPercent,
                onOptionSelected = viewModel::setOptionSelected,
                onAmountFees = viewModel::setAmountFees,
                onCreditQuotaValue = viewModel::setCreditQuotaValue,
                onBackNavigateClick = { navController.navigateUp() },
                onSaveButtonClick = viewModel::saveCustomerCreditFromClient
            )
        }

    }
}
