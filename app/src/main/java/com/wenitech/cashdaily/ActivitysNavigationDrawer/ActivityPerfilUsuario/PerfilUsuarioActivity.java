package com.wenitech.cashdaily.ActivitysNavigationDrawer.ActivityPerfilUsuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.wenitech.cashdaily.R;

public class PerfilUsuarioActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    // Todo: declarar textviewa
    private TextView textViewTituloNombrePerfil;
    private TextView textViewInicialNombre;
    private TextView textViewTituloTipoCuenta;
    private TextView textViewNombrePerfil;
    private TextView textViewSuscripcion;
    private TextView textViewCorreo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        configurarDatosUserenTextview();
    }

    private void configurarDatosUserenTextview() {
        mUser = mAuth.getCurrentUser();
        String nombreUsuario = mUser.getDisplayName();
        String correoUsuario = mUser.getEmail();

        textViewTituloNombrePerfil.setText(nombreUsuario);
        textViewNombrePerfil.setText(nombreUsuario);
        textViewCorreo.setText(correoUsuario);
        if (nombreUsuario.length() >= 1){
            textViewInicialNombre.setText(nombreUsuario.substring(0,1).toUpperCase());
        }
    }

    private void abrirDialogoCambiarNombre() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilUsuarioActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialogo_cambiar_nombre_usuario,null);
        final TextInputEditText editTextNuevoNombre = view.findViewById(R.id.edit_text_dialogo_nombre_usuario);

        builder.setView(view);
        builder.setTitle("Cambiar nombre");
        builder.setMessage("Este nombre sera visible para tus cobradores y puede que se use en algunas funciones de la App.");
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoNombre = editTextNuevoNombre.getText().toString().trim();
                if (nuevoNombre.isEmpty()){
                    Toast.makeText(PerfilUsuarioActivity.this, "No se han hecho cambios", Toast.LENGTH_SHORT).show();
                }else if (nuevoNombre.length()<= 3){
                    Toast.makeText(PerfilUsuarioActivity.this, "Escribe al menos 3 letras", Toast.LENGTH_SHORT).show();
                }else if (nuevoNombre.length() >= 35){
                    Toast.makeText(PerfilUsuarioActivity.this, "Nombre demasiado grande", Toast.LENGTH_SHORT).show();
                }else {
                    UserProfileChangeRequest update = new UserProfileChangeRequest.Builder().setDisplayName(nuevoNombre).build();
                    mUser.updateProfile(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PerfilUsuarioActivity.this, "Nombre Actualizado", Toast.LENGTH_SHORT).show();
                                configurarDatosUserenTextview();
                            } else {
                                Toast.makeText(PerfilUsuarioActivity.this, "No fue posible cambiar los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PerfilUsuarioActivity.this, "Cancelar", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
