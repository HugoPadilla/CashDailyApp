package com.wenitech.cashdaily.framework.navigation.nestedNavGraph

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wenitech.cashdaily.framework.features.client.customerCredit.CustomerCreditScreen
import com.wenitech.cashdaily.framework.features.client.customerCredit.viewModel.CustomerCreditViewModel
import com.wenitech.cashdaily.framework.navigation.CREDIT_ROUTE
import com.wenitech.cashdaily.framework.navigation.ClientDestinations

@ExperimentalMaterialApi
fun NavGraphBuilder.customerCreditNavGraph(navController: NavHostController) {
    navigation(
        startDestination = ClientDestinations.CustomerCredit.route,
        route = "$CREDIT_ROUTE/{idClient}/{refCredit}"
    ) {
        composable(
            route = ClientDestinations.CustomerCredit.route + "/{idClient}" + "/{refCredit}"
        ) { navBackStackEntire ->
            val idClient = navBackStackEntire.arguments?.getString("idClient")
            val refCredit = navBackStackEntire.arguments?.getString("refCredit")
            val viewModel: CustomerCreditViewModel = hiltViewModel()

            LaunchedEffect(key1 = Unit, block = {
                viewModel.setIdClient(idClient, refCredit)
            })

            val state = viewModel.uiState.collectAsState()
            CustomerCreditScreen(
                idClient = idClient,
                state = state.value,
                onNewQuota = {
                    viewModel.setNewQuota(
                        it.toDouble(),
                        idClient = idClient ?: "",
                        refCreditActive = refCredit ?: ""
                    )
                }
            )
        }
    }
}