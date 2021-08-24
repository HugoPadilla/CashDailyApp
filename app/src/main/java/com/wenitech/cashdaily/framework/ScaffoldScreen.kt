package com.wenitech.cashdaily.framework

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import kotlinx.coroutines.launch

@Composable
fun ScaffoldScreen(navController: NavController, screen: @Composable () -> Unit) {

    val bottomNavigationItems = listOf(
        IconScreens.Home,
        IconScreens.Clients,
        IconScreens.Caja,
        IconScreens.Informe
    )

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Appbar(onMenuClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            })
        },
        bottomBar = { BottomAppNavBar(navController, bottomNavigationItems) },
        scaffoldState = scaffoldState,
        drawerContent = { Drawer() }
    ){
        Column(modifier = Modifier.padding(it)) {
            screen()
        }
    }

}

@Composable
fun Drawer() {
    Column {
        Text(text = "First action")
        Text(text = "Second action")
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
fun BottomAppNavBar(navController: NavController, bottomNavigationItems: List<IconScreens>) {
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
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.label
                    )
                },
                onClick = {
                    if (currentRoute != screen.route){
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

@Preview
@Composable
fun PreviewScaffoldScreen() {
    val navController = rememberNavController()

    CashDailyTheme {
        ScaffoldScreen(navController = navController) {
            Text(text = "Content")
        }
    }
}