package com.wenitech.cashdaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CambiarPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Toolbar toolbar;
    private TextView editTextTitulo;
    private TextView editTextSubtitulo;
    private TextInputLayout textInputLayoutContraseñaActual, textInputLayoutNuevaContraseña, textInputLayoutConfirmarContraseña;
    private TextInputEditText editTextConstraseñaActual, editTextNuevaContraseña, editTextConfirmarNuevaContraseña;
    private MaterialButton buttonEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        configurarToolbar();
        configurarCastingView();
        configurarListenerButton();
    }

    private void configurarToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_cambiar_contraseña);
        toolbar.setTitle("Actualizar credenciales");
        toolbar.setNavigationIcon(R.drawable.ic_system_flecha_atras_blanco);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Todo: evento click botot atras
        finishAfterTransition();
        return false;
    }

    private void configurarListenerButton() {
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarContraseña();
            }
        });
    }

    private void cambiarContraseña() {
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
    }

    private void configurarCastingView() {
        editTextTitulo = findViewById(R.id.text_view_crear_cuenta_titulo);
        editTextSubtitulo = findViewById(R.id.text_view_cambiar_contraseña_subtitulo);

        textInputLayoutContraseñaActual = findViewById(R.id.text_input_layout_cambiar_contraseña_contraseña_actual);
        textInputLayoutNuevaContraseña = findViewById(R.id.text_input_layout_cambiar_contraseña_nueva_contraseña);
        textInputLayoutConfirmarContraseña = findViewById(R.id.text_input_layout_cambiar_contraseña_confirmar_nueva_contraseña);

        editTextConstraseñaActual = findViewById(R.id.edit_text_cambiar_contraseña_contraseña_actual);
        editTextNuevaContraseña = findViewById(R.id.edit_text_cambiar_contraseña_nueva_contraseña);
        editTextConfirmarNuevaContraseña = findViewById(R.id.edit_text_cambiar_contraseña_confirma_nueva_contraseña);
        buttonEnviar = findViewById(R.id.button_cambiar_contraseña_enviar);
    }
}
