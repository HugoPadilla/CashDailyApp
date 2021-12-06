package com.wenitech.cashdaily.framework.features.credit.registerCredit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.framework.component.button.IconButtonCustom
import com.wenitech.cashdaily.framework.component.button.PrimaryButtonExtended
import com.wenitech.cashdaily.framework.component.card.ClientItem
import com.wenitech.cashdaily.framework.component.edittext.CustomTextField
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@ExperimentalMaterialApi
@Composable
fun SelectedClientScreen(
    listClient: List<Client> = listOf(),
    valueSearch: String,
    onValueSearchChange: (String) -> Unit,
    onBackNavigateClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    onNextStepClick: (idClient: String) -> Unit,
    onNewClientClick: () -> Unit
) {

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButtonCustom(
                    iconDrawable = R.drawable.ic_arrow_left,
                    modifier = Modifier.padding(end = 28.dp)
                ) {
                    onBackNavigateClick()
                }

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(text = "Paso 1/2", style = MaterialTheme.typography.h6)
                        Text(text = "Selecionar cliente", style = MaterialTheme.typography.body2)
                    }
                }

            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp)
            ) {

                LazyColumn {

                    item {
                        Text(
                            modifier = Modifier.padding(top = 18.dp, bottom = 18.dp),
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.h4,
                            fontWeight = FontWeight(600),
                            text = stringResource(id = R.string.title_selected_client)
                        )
                    }

                    item {
                        Text(
                            text = "Busca y selecciona un cliente",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }

                    item {
                        CustomTextField(
                            value = valueSearch,
                            onValueChange = onValueSearchChange,
                            label = "Buscar cliente",
                            modifier = Modifier.padding(bottom = 8.dp),
                            trailingIcon = {
                                IconButton(
                                    onClick = { onSearchClick(valueSearch) }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_search),
                                        contentDescription = null
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                onSearchClick(valueSearch)
                            })
                        )
                    }

                    items(listClient) { client ->
                        ClientItem(
                            clientModel = client,
                            onClick = { idClient, _ ->
                                onNextStepClick(idClient)
                            },
                            backgroundColor = MaterialTheme.colors.background
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(68.dp))
                    }

                }

            }
        },
        floatingActionButton = {
            PrimaryButtonExtended(
                text = "Agregar cliente",
                onClick = onNewClientClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                iconDrawable = R.drawable.ic_user_plus
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewSelectedClientScreen() {
    CashDailyTheme {
        SelectedClientScreen(
            valueSearch = "",
            onValueSearchChange = {},
            onSearchClick = {},
            onNextStepClick = {},
            onNewClientClick = {},
            onBackNavigateClick = {}
        )
    }
}