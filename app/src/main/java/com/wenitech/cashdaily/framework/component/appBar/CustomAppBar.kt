package com.wenitech.cashdaily.framework.component.appBar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R

@Composable
fun CustomAppBar(
    @DrawableRes leadingIcon: Int,
    onLeadingIconClick: () -> Unit
) {
    TopAppBar(
        backgroundColor = Color.Unspecified,
        elevation = 0.dp,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        IconButton(
            onClick = onLeadingIconClick,
            modifier = Modifier
                .padding(start = 16.dp)
                .background(
                    MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(corner = CornerSize(8.dp))
                )
        ) {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = "Back button",
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Preview
@Composable
fun PreviewCustomAppBar() {
    MaterialTheme {
        CustomAppBar( leadingIcon = R.drawable.ic_arrow_left) {

        }
    }
}