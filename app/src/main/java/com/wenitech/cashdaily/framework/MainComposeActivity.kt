package com.wenitech.cashdaily.framework

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.features.authentication.AuthenticationActivity
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import com.wenitech.cashdaily.framework.utils.MainNavRoutes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainComposeActivity : ComponentActivity() {

    private lateinit var authStateListener: AuthStateListener
    private val auth: FirebaseAuth = getInstance()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFirebaseAuthListener()

        setContent {

            val navController = rememberNavController()

            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()

            val bottomNavigationItems = listOf(
                MainNavRoutes.Home,
                MainNavRoutes.Clients,
                MainNavRoutes.Caja,
                MainNavRoutes.Informe
            )

            CashDailyTheme {
                Scaffold(
                    topBar = {
                        Appbar(onMenuClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        })
                    },
                    bottomBar = {
                        BottomAppNavBar(
                            navController = navController,
                            bottomNavigationItems = bottomNavigationItems
                        )
                    },
                    drawerContent = {
                        Drawer(onCloseSession = {
                            auth.signOut()
                        })
                    },
                    scaffoldState = scaffoldState
                ) {
                    NavComposeApp(modifier = Modifier.padding(it), navController = navController) {
                    }
                }
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

@Composable
fun Drawer(onCloseSession: () -> Unit) {
    Column {
        Text(text = "First action")
        Text(text = "Second action")
        Button(onClick = onCloseSession) {
            Text(text = "Cerrar sesion")
        }
    }
}

@Composable
fun Appbar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Title")
        },
        navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun BottomAppNavBar(navController: NavController, bottomNavigationItems: List<MainNavRoutes>) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.secondary,
        elevation = 10.dp
    ) {
        bottomNavigationItems.forEach { screen ->

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            BottomNavigationItem(
                label = { Text(text = screen.label) },
                selected = currentRoute == screen.route,
                icon = {
                    if (currentRoute == screen.route){
                        // Todo: Establecer icono relleno para cuand este activado
                    } else {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.label
                        )
                    }
                },
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo = navController.graph.startDestinationId
                            launchSingleTop = true
                        }
                    }
                },
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
