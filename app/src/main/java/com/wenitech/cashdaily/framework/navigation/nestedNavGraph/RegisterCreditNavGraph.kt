package com.wenitech.cashdaily.framework.navigation.nestedNavGraph

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navigation
import com.wenitech.cashdaily.framework.features.client.listClient.ClientsComposeScreen
import com.wenitech.cashdaily.framework.features.credit.newCredit.RegisterCreditScreen
import com.wenitech.cashdaily.framework.features.credit.newCredit.RegisterCreditViewModel
import com.wenitech.cashdaily.framework.navigation.CREDIT_ROUTE
import com.wenitech.cashdaily.framework.navigation.ClientDestinations

@ExperimentalMaterialApi
fun NavGraphBuilder.registerCreditNavGraph(navController: NavHostController) {
    navigation(
        startDestination = ClientDestinations.SelectClient.route,
        route = CREDIT_ROUTE
    ) {

        composable(route = ClientDestinations.SelectClient.route) {
            // Todo: Composable selected client
            ClientsComposeScreen(onNavigateNewClient = { /*TODO*/ }, onNavigateClientInfo = { idClient, refClient ->  })
        }

        composable(
            route = ClientDestinations.RegisterCredit.route + "/{idClient}",
            arguments = listOf(
                navArgument(name = "idClient") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->

            val viewModel: RegisterCreditViewModel = hiltViewModel()

            val idClientArg = navBackStackEntry.arguments?.getString("idClient")
            LaunchedEffect(key1 = Unit, block = {
                idClientArg?.let { viewModel.setIdClient(it) }
                Log.d("LAUNCHED_EFFECT", idClientArg.toString())
            })
            RegisterCreditScreen()
        }
    }
}