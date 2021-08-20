package com.wenitech.cashdaily.framework.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.framework.commons.clientsData
import com.wenitech.cashdaily.framework.composable.commons.ClientItem
import com.wenitech.cashdaily.framework.features.client.listClient.viewModel.ClientViewModel
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@ExperimentalMaterialApi
@Composable
fun ClientsComposeScreen(
    viewModel: ClientViewModel,
    onFloatingButtonClick: () -> Unit,
    onClientClick: (idClient: String, refCredit: String) -> Unit
) {

    val listClient by viewModel.listClient.collectAsState()

    CashDailyTheme {
        ClientsContent(
            listClint = listClient,
            onFloatingButtonClick = onFloatingButtonClick,
            onClientClick = onClientClick
        )
    }
}


@ExperimentalMaterialApi
@Composable
private fun ClientsContent(
    listClint: List<Client>,
    onFloatingButtonClick: () -> Unit,
    onClientClick: (idClient: String, refCredit: String) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FabButtonNormal(
                iconDrawable = R.drawable.ic_user_plus,
                contentDescription = "Add new client",
                onFloatingButtonClick = onFloatingButtonClick
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.surface)
        ) {

            LazyColumn {

                item {
                    var name by remember { mutableStateOf("") }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Buscar cliente") }
                    )
                }

                items(listClint) { client ->
                    ClientItem(
                        client = client,
                        onClick = onClientClick
                    )
                }

            }
        }

    }
}

@Composable
fun FabButtonNormal(
    @DrawableRes iconDrawable: Int,
    contentDescription: String,
    onFloatingButtonClick: () -> Unit
) {
    FloatingActionButton(onClick = onFloatingButtonClick) {
        Icon(
            painter = painterResource(id = iconDrawable),
            contentDescription = contentDescription
        )
    }
}

@ExperimentalMaterialApi
@Preview(name = "Light Mode")
@Composable
fun PreviewContent() {
    CashDailyTheme {
        ClientsContent(
            listClint = clientsData,
            onFloatingButtonClick = { /*TODO*/ },
            onClientClick = { idClient, refCredit ->

            }
        )
    }
}
