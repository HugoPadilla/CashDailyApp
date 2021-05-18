package com.wenitech.cashdaily.features.authentication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wenitech.cashdaily.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentResetPasword : Fragment() {

    /**
     * FragmentResetPassword: Fragment para cambiar la contraseña cuando ya has iniciado sesion
     */

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_pasword, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

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

    private void configurarCuadroDialogo(String titulo, String mensaje, final boolean complete) {
        final MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setCancelable(false);
        materialAlertDialogBuilder.setTitle(titulo);
        materialAlertDialogBuilder.setMessage(mensaje);
        materialAlertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (complete) {
                    finishAfterTransition();
                }
            }
        });
        materialAlertDialogBuilder.show();
    }

    private boolean isFormularioValido() {
        boolean valido = true;

        String contraseñaActual = editTextConstraseñaActual.getText().toString().trim();
        String nuevaContraseña = editTextNuevaContraseña.getText().toString().trim();
        String confirmarContraseña = editTextConfirmarNuevaContraseña.getText().toString().trim();
        if (contraseñaActual.isEmpty()) {
            textInputLayoutContraseñaActual.setError("Escribe tu contraseña actual");
            valido = false;
        } else if (nuevaContraseña.isEmpty()) {
            textInputLayoutNuevaContraseña.setError("Escribe una contraseña nueva");
            valido = false;
        } else if (contraseñaActual.equals(nuevaContraseña)) {
            textInputLayoutNuevaContraseña.setError("Escribe una contraseña diferente");
            valido = false;
        } else if (nuevaContraseña.length() < 8) {
            textInputLayoutNuevaContraseña.setError("Debe tener al menos 8 caracteres");
            valido = false;
        } else if (confirmarContraseña.isEmpty()) {
            textInputLayoutConfirmarContraseña.setError("Confirma tu nueva contraseña");
            valido = false;
        } else if (!nuevaContraseña.equals(confirmarContraseña)) {
            textInputLayoutConfirmarContraseña.setError("Esta contraseña no coincide");
            editTextConfirmarNuevaContraseña.setText("");
            valido = false;
        }

        return valido;
    }*/
}