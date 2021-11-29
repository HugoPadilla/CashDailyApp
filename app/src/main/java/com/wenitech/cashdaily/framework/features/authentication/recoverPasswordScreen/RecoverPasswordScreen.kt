package com.wenitech.cashdaily.framework.features.authentication.recoverPasswordScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.domain.common.Status
import com.wenitech.cashdaily.framework.component.button.PrimaryButtonExtended
import com.wenitech.cashdaily.framework.component.button.TextButtonRegister
import com.wenitech.cashdaily.framework.component.edittext.CustomTextField
import com.wenitech.cashdaily.framework.features.authentication.AuthDestinations
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import kotlinx.coroutines.flow.collectLatest

/**
 * Recuper contraseña cuando la has olvidado
 * Envia un correo de recuperacion de contraseña
 */

@Composable
fun RecoverPasswordScreen(
    navController: NavController, viewModel: RecoverPasswordViewModel
) {

    val emailValue by viewModel.emailRecover.collectAsState()
    val emailValueMessageError by viewModel.emailValueMessageError.collectAsState()
    val validEmail by derivedStateOf {
        emailValueMessageError.isNullOrBlank() && emailValue.isNotEmpty()
    }

    val localContext = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.resultEmailRecover.collectLatest { value ->
            when (value.status) {
                Status.LOADING -> {
                    Toast.makeText(localContext, "Enviando email...", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    Toast.makeText(
                        localContext,
                        "Se envio una correo. Revisa tu bandeja",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.clearEditText()
                }
                Status.COLLICION -> TODO()
                Status.FAILED -> {
                    Toast.makeText(
                        localContext,
                        "Presentamos inconvenientes. Intenta mas tarde.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    RecoverPasswordContent(
        onNavigationUp = { navController.navigateUp() },
        onNavigationRegister = { navController.navigate(AuthDestinations.SingIn.route) },
        onSendEmailListener = { viewModel.sendEmailRecover(it) },
        onValueChange = { viewModel.emailValueChange(it) },
        value = emailValue,
        emailValueMessageError = emailValueMessageError,
        isValidEmail = validEmail
    )
}

@Composable
private fun RecoverPasswordContent(
    onNavigationUp: () -> Unit,
    onNavigationRegister: () -> Unit,
    onSendEmailListener: (email: String) -> Unit,
    onValueChange: (value: String) -> Unit,
    value: String,
    isValidEmail: Boolean,
    emailValueMessageError: String? = null
) {

    val scrollSate = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.Unspecified,
                elevation = 0.dp
            ) {
                IconButton(
                    onClick = { onNavigationUp() },
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(corner = CornerSize(8.dp))
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "Back button"
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scrollSate),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(top = 42.dp, bottom = 12.dp),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight(600),
                text = "Restablecer contraseña"
            )

            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                style = MaterialTheme.typography.body1,
                text = "Enviaremos instrucciones sobre cómo restablecer su contraseña a la direccion de correo electronico que se ha registrado con nosotros."
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                label = "Correo electronico",
                value = value,
                messageError = emailValueMessageError,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mail),
                        contentDescription = null
                    )
                },
                onValueChange = onValueChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButtonExtended(
                onClick = { onSendEmailListener(value) },
                enabled = isValidEmail,
                text = "Enviar email"
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButtonRegister(
                onNavigationRegisterListener = { onNavigationRegister() },
                modifier = Modifier.padding(bottom = 32.dp, top = 16.dp),
                textButton = "Registrate"
            )
        }
    }
}

@Preview
@Composable
fun PreviewRecoverPasswordContent() {
    CashDailyTheme {
        RecoverPasswordContent(
            onNavigationUp = { /*TODO*/ },
            onNavigationRegister = { /* TODO */ },
            onSendEmailListener = { /*TODO*/ },
            onValueChange = {},
            value = "",
            isValidEmail = false
        )
    }
}