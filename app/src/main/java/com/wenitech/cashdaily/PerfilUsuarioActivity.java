package com.wenitech.cashdaily;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                break;
            case R.id.card_view_perfil_usuario_suscripcion:
                break;
            case R.id.card_view_perfil_usuario_correo:
                break;
            case R.id.card_view_perfil_usuario_contraseña:
                break;
            case R.id.card_view_perfil_usuario_eliminar:
                break;
        }
    }
}
