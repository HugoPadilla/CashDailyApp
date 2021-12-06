package com.wenitech.cashdaily.framework.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconButtonCustom(
    @DrawableRes iconDrawable: Int,
    modifier: Modifier = Modifier,
    contentDescriptor: String? = null,
    iconTint: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(
                color = MaterialTheme.colors.primary.copy(alpha = .2f),
                shape = RoundedCornerShape(corner = CornerSize(5.dp))
            )
    ) {
        Icon(
            painter = painterResource(id = iconDrawable),
            contentDescription = contentDescriptor,
            tint = iconTint
        )
    }
}
