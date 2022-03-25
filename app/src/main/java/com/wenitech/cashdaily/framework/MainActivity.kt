package com.wenitech.cashdaily.framework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.wenitech.cashdaily.framework.component.appBar.PrimaryAppBar
import com.wenitech.cashdaily.framework.component.appBar.PrimaryBottomBar
import com.wenitech.cashdaily.framework.component.drawer.PrimaryDrawer
import com.wenitech.cashdaily.framework.features.authentication.navigation.AuthDestinations
import com.wenitech.cashdaily.framework.features.authentication.navigation.AuthDestinationsRoot
import com.wenitech.cashdaily.framework.navigation.BottomNavDestinations
import com.wenitech.cashdaily.framework.navigation.MainGraph
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val userAuthenticationState by viewModel.getAuthState

            val navController = rememberNavController()
            val currentRoute = navController
                .currentBackStackEntryFlow
                .collectAsState(initial = navController.currentBackStackEntry)

            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()

            val bottomNavigationItems = listOf(
                BottomNavDestinations.Home,
                BottomNavDestinations.Clients,
                BottomNavDestinations.Box,
                BottomNavDestinations.Report
            )

            val (labelAppBar, onChangeLabel) = remember {
                mutableStateOf("Home")
            }

            val density = LocalDensity.current

            LaunchedEffect(userAuthenticationState) {
                if (userAuthenticationState) {
                    navController.navigate(BottomNavDestinations.Home.route) {
                        popUpTo(AuthDestinationsRoot) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                } else {
                    if (currentRoute.value?.destination?.route != AuthDestinations.Start.route) {
                        navController.navigate(AuthDestinationsRoot)
                        navController.graph.clear()
                        scaffoldState.drawerState.close()
                    }
                }
            }

            CashDailyTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    drawerContent = {
                        PrimaryDrawer {
                            viewModel.signOut()
                            /*navController.navigate(AuthDestinations.Start.route)
                            navController.graph.clear()*/
                        }
                    },
                    drawerGesturesEnabled = userAuthenticationState,
                    topBar = {
                        AnimatedVisibility(
                            visible = userAuthenticationState,
                            enter = slideInVertically(
                                initialOffsetY = {
                                    with(density) { -40.dp.roundToPx() }
                                }
                            ) + expandVertically(
                                expandFrom = Alignment.Top
                            ) + fadeIn(initialAlpha = .3f),
                            exit = slideOutVertically() + fadeOut()
                        ) {
                            PrimaryAppBar(title = labelAppBar) {
                                scope.launch { scaffoldState.drawerState.open() }
                            }
                        }
                    },
                    content = {
                        MainGraph(
                            modifier = Modifier.padding(it),
                            navController = navController
                        )
                    },
                    bottomBar = {
                        AnimatedVisibility(
                            visible = userAuthenticationState,
                            enter = slideInVertically(
                                initialOffsetY = {
                                    with(density) { -40.dp.roundToPx() }
                                }
                            ) + expandVertically(
                                expandFrom = Alignment.Top
                            ) + fadeIn(initialAlpha = .3f),
                            exit = slideOutVertically() + fadeOut()
                        ) {
                            PrimaryBottomBar(
                                navController = navController,
                                bottomNavigationItems = bottomNavigationItems,
                                onChangeLabel = onChangeLabel
                            )
                        }
                    }
                )
            }
        }
    }
}
