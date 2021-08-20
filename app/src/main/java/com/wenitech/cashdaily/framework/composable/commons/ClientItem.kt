package com.wenitech.cashdaily.framework.composable.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.framework.commons.clientsData
import com.wenitech.cashdaily.framework.ui.theme.BackgroundLight
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@ExperimentalMaterialApi
@Composable
fun ClientItem(
    client: Client,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    cornerRadius: Dp = 10.dp,
    onClick: (idClient: String, refCredit: String) -> Unit
) {

    Card(
        elevation = elevation,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(cornerRadius),
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onClick(client.id!!, client.refCredit!!.id)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape)
                    .background(BackgroundLight),
                painter = painterResource(id = R.drawable.ic_profile_empty),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(2f)
            ) {
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    text = client.fullName
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    style = MaterialTheme.typography.caption,
                    text = client.direction
                )
            }
        }
    }

}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewClientItem() {
    CashDailyTheme {
        ClientItem(client = clientsData[0], onClick = { idClient, refCredit ->

        })
    }
}