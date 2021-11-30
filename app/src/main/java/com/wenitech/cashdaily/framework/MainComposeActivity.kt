package com.wenitech.cashdaily.framework

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.wenitech.cashdaily.framework.component.appBar.PrimaryAppBar
import com.wenitech.cashdaily.framework.component.appBar.PrimaryBottomBar
import com.wenitech.cashdaily.framework.component.drawer.PrimaryDrawer
import com.wenitech.cashdaily.framework.features.authentication.AuthenticationActivity
import com.wenitech.cashdaily.framework.navigation.BottomNavDestinations
import com.wenitech.cashdaily.framework.navigation.MainNavGraph
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainComposeActivity : ComponentActivity() {

    private lateinit var authStateListener: AuthStateListener
    private val auth: FirebaseAuth = getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFirebaseAuthListener()

        setContent {

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

            val (showBottomBar, onShowBottomBar) = remember {
                mutableStateOf(true)
            }

            onShowBottomBar(
                when (currentRoute.value?.destination?.route) {
                    BottomNavDestinations.Home.route -> true
                    BottomNavDestinations.Clients.route -> true
                    BottomNavDestinations.Box.route -> true
                    BottomNavDestinations.Report.route -> true
                    else -> false
                }
            )

            val density = LocalDensity.current

            CashDailyTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    drawerContent = {
                        PrimaryDrawer {
                            auth.signOut()
                        }
                    },
                    topBar = {
                        AnimatedVisibility(
                            visible = showBottomBar,
                            enter = slideInVertically(
                                initialOffsetY = {
                                    with(density) { -40.dp.roundToPx() }
                                }
                            ) + expandVertically(
                                expandFrom = Alignment.Top
                            ) + fadeIn(initialAlpha = .3f),
                            exit = slideOutVertically()
                        ) {
                            PrimaryAppBar(title = labelAppBar) {
                                scope.launch { scaffoldState.drawerState.open() }
                            }
                        }
                    },
                    content = {
                        MainNavGraph(
                            modifier = Modifier.padding(it),
                            navController = navController
                        )
                    },
                    bottomBar = {
                        AnimatedVisibility(
                            visible = showBottomBar,
                            enter = slideInVertically(
                                initialOffsetY = {
                                    with(density) { -40.dp.roundToPx() }
                                }
                            ) + expandVertically(
                                expandFrom = Alignment.Top
                            ) + fadeIn(initialAlpha = .3f),
                            exit = slideOutVertically()
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

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authStateListener)
    }

    private fun setupFirebaseAuthListener() {
        authStateListener = AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                /* viewModel.setStateAuth(AuthenticationStatus.UNAUTHENTICATED)
                 navigateToLogin()*/
                Toast.makeText(this, "Autenticado", Toast.LENGTH_SHORT).show()
            } else {
                /*viewModel.setStateAuth(AuthenticationStatus.AUTHENTICATED)*/
                Toast.makeText(this, "No authenticado", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, AuthenticationActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)

                startActivity(intent)
                finish()
            }
        }
    }
}
