package com.wenitech.cashdaily.ActivitysNavigationDrawer.ActivityPerfilUsuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.wenitech.cashdaily.CambiarPasswordActivity;
import com.wenitech.cashdaily.R;

import java.util.zip.Inflater;

public class PerfilUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    // Todo: declarar textviewa
    private TextView textViewTituloNombrePerfil;
    private TextView textViewInicialNombre;
    private TextView textViewTituloTipoCuenta;
    private TextView textViewNombrePerfil;
    private TextView textViewSuscripcion;
    private TextView textViewCorreo;

    // cardview listener
    private CardView cardViewActualizarNombre;
    private CardView cardViewSubscripcion;
    private CardView cardViewActualizarCorreo;
    private CardView cardViewActualizarContraseña;
    private CardView cardViewEliminarCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_perfil_usuario);
        configurarToolbar();
        castingView();
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

    private void castingView() {
        textViewTituloNombrePerfil = findViewById(R.id.text_view_perfil_usuario_titulo_nombre_perfil);
        textViewInicialNombre = findViewById(R.id.text_view_perfil_usuario_inicial_nombre);
        textViewTituloTipoCuenta = findViewById(R.id.text_view_perfil_usuario_titulo_tipo_cuenta);
        textViewNombrePerfil = findViewById(R.id.text_view_perfil_usuario_nombre);
        textViewSuscripcion = findViewById(R.id.text_view_perfil_usuario_subscripcion);
        textViewCorreo = findViewById(R.id.text_view_perfil_usuario_correo);

        // casting card view and Listener
        cardViewActualizarNombre = findViewById(R.id.card_view_perfil_usuario_nombre);
        cardViewActualizarNombre.setOnClickListener(this);
        cardViewSubscripcion = findViewById(R.id.card_view_perfil_usuario_suscripcion);
        cardViewSubscripcion.setOnClickListener(this);
        cardViewActualizarCorreo = findViewById(R.id.card_view_perfil_usuario_correo);
        cardViewActualizarCorreo.setOnClickListener(this);
        cardViewActualizarContraseña = findViewById(R.id.card_view_perfil_usuario_contraseña);
        cardViewActualizarContraseña.setOnClickListener(this);
        cardViewEliminarCuenta = findViewById(R.id.card_view_perfil_usuario_eliminar);
        cardViewEliminarCuenta.setOnClickListener(this);
    }

    private void configurarToolbar() {
        // Todo: configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_perfil_usuario);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_view_perfil_usuario_nombre:
                abrirDialogoCambiarNombre();
                break;
            case R.id.card_view_perfil_usuario_suscripcion:
                break;
            case R.id.card_view_perfil_usuario_correo:
                break;
            case R.id.card_view_perfil_usuario_contraseña:
                Intent intent = new Intent(PerfilUsuarioActivity.this, CambiarPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.card_view_perfil_usuario_eliminar:
                break;
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
