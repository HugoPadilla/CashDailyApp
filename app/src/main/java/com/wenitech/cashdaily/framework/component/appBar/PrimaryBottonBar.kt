package com.wenitech.cashdaily.framework.component.appBar

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wenitech.cashdaily.framework.navigation.BottomNavDestinations

@Composable
fun PrimaryBottomBar(
    navController: NavController,
    bottomNavigationItems: List<BottomNavDestinations>,
    onChangeLabel: (String) -> Unit
) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.secondary,
        elevation = 10.dp
    ) {

        bottomNavigationItems.forEach { screen ->

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination

            BottomNavigationItem(
                label = { Text(text = screen.label) },
                selected = currentRoute?.hierarchy?.any { it.route == screen.route } == true,
                icon = {
                    if (currentRoute?.route == screen.route) {
                        // Todo: Establecer icono relleno para cuando este selecionado
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.label
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.label
                        )
                    }
                },
                onClick = {
                    onChangeLabel(screen.label)
                    navController.navigate(screen.route) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}