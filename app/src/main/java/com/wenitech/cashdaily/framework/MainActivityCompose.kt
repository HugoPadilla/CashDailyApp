package com.wenitech.cashdaily.framework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CashDailyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    BodyContent()
                }
            }
        }
    }
}

@Composable
private fun BodyContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Home Activity") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_inicio),
                            contentDescription = "Pantalla de inicio"
                        )
                    },
                    label = {
                        Text(text = "Home")
                    },
                    selected = true,
                    onClick = {
                        /*TODO*/
                    }
                )

                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clientes),
                            contentDescription = "Lista de clientes"
                        )
                    },
                    label = {
                        Text(text = "Clientes")
                    },
                    selected = false,
                    onClick = {
                        /*TODO*/
                    }
                )

                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_box),
                            contentDescription = "Caja"
                        )
                    },
                    label = {
                        Text(text = "Caja")
                    },
                    selected = false,
                    onClick = {
                        /*TODO*/
                    }
                )

                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_informe),
                            contentDescription = "Estadisticas"
                        )
                    },
                    label = {
                        Text(text = "Estadisticas")
                    },
                    selected = false,
                    onClick = {
                        /*TODO*/
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_plus), contentDescription = null)
            }
        }
    ) {
        Text(text = "Hello word")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    CashDailyTheme {
        BodyContent()
    }
}