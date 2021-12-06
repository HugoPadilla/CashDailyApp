package com.wenitech.cashdaily.framework.navigation.nestedNavGraph

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wenitech.cashdaily.framework.features.client.listClient.viewModel.ClientViewModel
import com.wenitech.cashdaily.framework.features.credit.registerCredit.RegisterCreditScreen
import com.wenitech.cashdaily.framework.features.credit.registerCredit.RegisterCreditViewModel
import com.wenitech.cashdaily.framework.features.credit.registerCredit.SelectedClientScreen
import com.wenitech.cashdaily.framework.navigation.CREDIT_ROUTE
import com.wenitech.cashdaily.framework.navigation.ClientDestinations

@ExperimentalMaterialApi
fun NavGraphBuilder.registerCreditNavGraph(navController: NavHostController) {
    navigation(
        startDestination = ClientDestinations.SelectClient.route,
        route = CREDIT_ROUTE
    ) {

        composable(route = ClientDestinations.SelectClient.route) {

            val viewModel: ClientViewModel = hiltViewModel()
            val uiState by viewModel.uiStates.collectAsState()

            val (valueSearch, onValueSearchChange) = remember {
                mutableStateOf("")
            }

            SelectedClientScreen(
                listClient = uiState.listClient,
                valueSearch = valueSearch,
                onValueSearchChange = onValueSearchChange,
                onBackNavigateClick = { navController.navigateUp() },
                onSearchClick = viewModel::searchClientByName,
                onNextStepClick = {
                    navController.navigate(route = ClientDestinations.RegisterCredit.route + "/$it")
                },
                onNewClientClick = {
                    // Todo: Navegar al formulario para agregar un nuevo cliente
                })

        }

        composable(
            route = ClientDestinations.RegisterCredit.route + "/{idClient}"
        ) { navBackStackEntry ->

            val viewModel: RegisterCreditViewModel = hiltViewModel()

            val idClientArg = navBackStackEntry.arguments?.getString("idClient")
            LaunchedEffect(key1 = Unit, block = {
                idClientArg?.let { viewModel.setIdClient(it) }
            })

            val uiState by viewModel.registerCreditUiState.collectAsState()

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