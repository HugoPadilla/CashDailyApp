package com.wenitech.cashdaily.framework.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.framework.DataExample.clientsData
import com.wenitech.cashdaily.framework.ui.theme.BackgroundLight
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

@ExperimentalMaterialApi
@Composable
fun ClientItem(
    clientModel: Client,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    elevation: Dp = 0.dp,
    cornerRadius: Dp = 10.dp,
    onClick: (idClient: String, refCredit: String) -> Unit,
    colorIndicator: Color? = null
) {

    Card(
        elevation = elevation,
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(cornerRadius),
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onClick(clientModel.id ?: "", clientModel.refCredit ?: "")
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
                    .weight(.9f)
            ) {
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    text = clientModel.fullName
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    style = MaterialTheme.typography.caption,
                    text = "${clientModel.city}, ${clientModel.direction}"
                )
            }

            colorIndicator?.let { color ->
                Box(
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .border(2.dp, color.copy(alpha = .2f), RoundedCornerShape(15.dp))
                        .background(color = color.copy(alpha = .4f))
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
        ClientItem(clientModel = clientsData[0], onClick = { idClient, refCredit ->

        })
    }
}