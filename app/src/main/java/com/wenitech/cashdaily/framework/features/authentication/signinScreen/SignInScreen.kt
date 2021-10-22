package com.wenitech.cashdaily.framework.features.authentication.signinScreen

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.component.commons.CustomTextField
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.uiState.SignInUiState
import com.wenitech.cashdaily.framework.features.authentication.signinScreen.viewModel.SignInViewModel
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel,
    startActivityMain: () -> Unit
) {

    val localContext = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.signInUiState.collectLatest { value ->
            when (value) {
                SignInUiState.Loading -> {
                    // Todo: Loading: Crear estado de carga en la interfas de usuario
                    Toast.makeText(localContext, "Creando cuenta de usuario", Toast.LENGTH_SHORT)
                        .show()
                }
                SignInUiState.Success -> {
                    startActivityMain()
                }
                is SignInUiState.Collision -> {
                    // Todo: Mostrar mensaje de colicicon de cuenta
                    Toast.makeText(
                        localContext,
                        "Ya existe una cuenta de usuario con este correo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is SignInUiState.Failed -> {
                    // Todo: Mostrar mensaje de error al crear cuenta
                    Toast.makeText(
                        localContext,
                        "Presentamos inconveniente al crear tu cuenta",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    val email by viewModel.email.collectAsState()
    val emailMessageError by viewModel.emailMessageError.collectAsState()

    val password by viewModel.password.collectAsState()
    val passwordMessageError by viewModel.passwordMessageError.collectAsState()

    val passwordConfirm by viewModel.passwordConfirm.collectAsState()
    val passwordConfirmMessageError by viewModel.passwordConfirmMessageError.collectAsState()

    val isValidEmail by viewModel.isValidEmail.collectAsState()
    val isValidPassword by viewModel.isValidPassword.collectAsState()
    val isValidPasswordConfirm by viewModel.isValidPasswordConfirm.collectAsState()

    val isValidForm by derivedStateOf {
        isValidEmail && isValidPassword && isValidPasswordConfirm
    }

    SignInContent(
        onNavigationUp = { navController.navigateUp() },
        email = email,
        emailMsgError = emailMessageError,
        password = password,
        passwordMsgError = passwordMessageError,
        passwordConfirm = passwordConfirm,
        passwordConfirmMsgError = passwordConfirmMessageError,
        isButtonEnable = isValidForm,
        onEmailChange = { viewModel.onEmailChange(it) },
        onPasswordChange = { viewModel.onPasswordChange(it) },
        onPasswordConfirmChange = { viewModel.onPasswordConfirmChange(it) },
        onSignInListener = { viewModel.doSignIn(email = email, password = password) }
    )
}

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    onNavigationUp: () -> Unit,
    email: String,
    emailMsgError: String?,
    password: String,
    passwordMsgError: String?,
    passwordConfirm: String,
    passwordConfirmMsgError: String?,
    isButtonEnable: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
    onSignInListener: () -> Unit
) {

    val scroll = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
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
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scroll)
                .padding(paddingValue),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .padding(top = 42.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight(600),
                text = "Registrarse",
            )

            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                style = MaterialTheme.typography.body1,
                text = "Crear una cuenta con tu correo personal eviaremos un email de confirmacion.",
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                CustomTextField(
                    label = "Email",
                    value = email,
                    icon = R.drawable.ic_mail,
                    onValueChange = onEmailChange,
                    messageError = emailMsgError
                )

                CustomTextField(
                    label = "Password",
                    value = password,
                    icon = R.drawable.ic_lock,
                    onValueChange = onPasswordChange,
                    messageError = passwordMsgError,
                )

                CustomTextField(
                    label = "Password confirm",
                    value = passwordConfirm,
                    icon = R.drawable.ic_lock,
                    onValueChange = onPasswordConfirmChange,
                    messageError = passwordConfirmMsgError
                )

                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    text = "Al presionar el boton crear <cuenta esta>, aceptando las politicas de privacida de Cash Daily App"
                )

            }

            Button(
                enabled = isButtonEnable,
                onClick = { onSignInListener() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(46.dp)
            ) {
                Text(text = "Crear cuenta")
            }

        }
    }

}

@Preview
@Composable
fun PreviewSignIn() {
    CashDailyTheme {
        SignInContent(
            onNavigationUp = {},
            email = "",
            emailMsgError = null,
            password = "",
            passwordMsgError = null,
            passwordConfirm = "",
            passwordConfirmMsgError = null,
            onEmailChange = {},
            onPasswordChange = {},
            onPasswordConfirmChange = {},
            isButtonEnable = false
        ) {

        }
    }
}