package com.wenitech.cashdaily.framework.features.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wenitech.cashdaily.R

@Composable
fun StartAuthenticationScreen(
    onNavigationLoginWithEmail: () -> Unit,
    onNavigationLoginWithGoogle: () -> Unit
) {
    Scaffold(modifier = Modifier) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(bottom = 42.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_modey_transparent),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.8f)
            )

            Button(
                onClick = onNavigationLoginWithEmail,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Continua con Email")
            }

            Button(
                onClick = onNavigationLoginWithGoogle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Continua con Google")
            }

        }
    }
}