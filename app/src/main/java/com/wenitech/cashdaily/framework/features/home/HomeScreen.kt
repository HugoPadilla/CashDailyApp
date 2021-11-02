package com.wenitech.cashdaily.framework.features.home

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.entities.Ruta
import com.wenitech.cashdaily.framework.commons.routesData
import com.wenitech.cashdaily.framework.component.commons.CashAvailableCardView
import com.wenitech.cashdaily.framework.component.commons.PrimaryButtonExtended
import com.wenitech.cashdaily.framework.features.authentication.AuthenticationActivity
import com.wenitech.cashdaily.framework.features.home.viewModel.HomeFragmentViewModel
import com.wenitech.cashdaily.framework.ui.theme.BackgroundLight
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeFragmentViewModel = viewModel(),
    onNewCreditClick: () -> Unit
) {

    val context = LocalContext.current
    val boxState by viewModel.homeUiState.collectAsState()
    val routesState by viewModel.routeUiState.collectAsState()

    HomeContent(box = boxState, routes = routesState, onNewCreditClick = {
        context.startActivity(Intent(context, AuthenticationActivity::class.java))
    })

}

@Composable
fun HomeContent(box: Box, routes: List<Ruta>, onNewCreditClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 0.dp)
    ) {

        CreditList(box, routes, onNewCreditClick = onNewCreditClick)

    }
}

@Composable
fun CreditList(box: Box, rutas: List<Ruta>, onNewCreditClick: () -> Unit) {

    LazyColumn {

        item {
            CashAvailableCardView(
                money = box.totalCash,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )
        }

        item {
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.h6,
                text = "Creditos activos"
            )
        }

        item {
            PrimaryButtonExtended(
                onClick = onNewCreditClick,
                modifier = Modifier.padding(bottom = 10.dp),
                text = "NUEVO CREDITO"
            )
        }

        items(rutas) { credit ->
            RutaItem(client = credit, modifier = Modifier)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RutaItem(client: Ruta, modifier: Modifier) {

    Card(
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            // Todo: to click client
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape)
                    .background(BackgroundLight),
                painter = painterResource(id = R.drawable.ic_profile_empty),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = modifier
                    .padding(start = 16.dp)
                    .weight(2f)
            ) {
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    text = client.name
                )
                Text(
                    style = MaterialTheme.typography.caption,
                    text = client.authorId
                )
            }
        }
    }

}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

/**
 * Preview scope
 */
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CashDailyTheme {
        HomeContent(box = Box(totalCash = 126000.00), routes = routesData) {

        }
    }
}
