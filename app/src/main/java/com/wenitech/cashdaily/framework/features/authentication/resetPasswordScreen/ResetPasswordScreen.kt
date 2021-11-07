package com.wenitech.cashdaily.framework.features.authentication.resetPasswordScreen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme

/**
 * Screen compose para cambiar la contraseña cuando ya se ha iniciado sesion
 */
@Composable
fun ResetPasswordScreen() {

    ResetPasswordContent()
    /*private void cambiarContraseña() {
        if (mAuth.getCurrentUser() != null) {
            if (isFormularioValido()) {
                String passwordActual = editTextConstraseñaActual.getText().toString().trim();
                final String newPassword = editTextNuevaContraseña.getText().toString().trim();

                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), passwordActual);
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            actualizarPassword(newPassword);
                        } else {
                            configurarCuadroDialogo("Autenticacion", "No pudimo verificar tu identidad. Por favor, intenta nuevamnte", false);
                        }
                    }
                });
            }
        }
    }

    private void actualizarPassword(String newPassword) {
        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    configurarCuadroDialogo("Actualizacion completada", "Su contraseña se ha cambiado con exito. Ahora puede acceder con sus nuevas credenciales", true);
                } else {
                    configurarCuadroDialogo("Tenemos un problemas", "No se pudo cambiar tu contraseña. Por favor, intenta nuevamente", false);
                }
            }
        });
    }
*/
}

@Composable
private fun ResetPasswordContent() {
    Scaffold {
        Text(text = "Reset password screen")
    }
}

@Preview
@Composable
fun PreviewResetPassword(){
    CashDailyTheme {
        ResetPasswordContent()
    }
}