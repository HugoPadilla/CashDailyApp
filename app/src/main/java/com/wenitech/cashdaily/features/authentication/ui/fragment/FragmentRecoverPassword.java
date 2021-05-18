package com.wenitech.cashdaily.features.authentication.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenitech.cashdaily.databinding.FragmentRecoverPasswordBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentRecoverPassword extends Fragment {

    FragmentRecoverPasswordBinding binding;

    /**
     * Recuper contraseña cuando la has olvidado
     * Envia un correo de recuperacion de contraseña
     */
    public FragmentRecoverPassword() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRecoverPasswordBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

   /* private void configurarListener() {
        buttonEnviar.setOnClickListener(this);
        textViewRegistrarse.setOnClickListener(this);
    }

    private void configurarCasting() {
        editTextEmail = findViewById(R.id.edit_text_reset_password_email);
        buttonEnviar = findViewById(R.id.button_reset_password_enviar_email);
        textViewRegistrarse = findViewById(R.id.text_view_registrarte);
    }

    private void iniciarToolbar() {
        Toolbar toolbarActivity = findViewById(R.id.toolbar_credito_vacio);
        toolbarActivity.setTitle("Recuperar cuenta");
        toolbarActivity.setNavigationIcon(R.drawable.ic_system_flecha_atras_blanco);
        setSupportActionBar(toolbarActivity);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Todo: evento click botot atras
        finishAfterTransition();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_reset_password_enviar_email:
                enviarEmailResetPassword();
                break;
            case R.id.text_view_registrarte:
                *//*startActivity(new Intent(ResetPasswordActivity.this, SingInActivity.class));
                finish();*//*
                break;
        }
    }

    private void enviarEmailResetPassword() {
        if (isFormularioValido()) {
            String emailSend = editTextEmail.getText().toString().trim();
            mAuth.sendPasswordResetEmail(emailSend).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        abrirCuadroDeDialogoSucces("Se ha enviado un mensaje a tu correo electronico. Encontraras instruciones para recupaera tu cuenta.");
                    } else {
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        switch (errorCode) {
                            case "ERROR_USER_NOT_FOUND":
                                abrirCuadroDialogoCuentaNoExist( "No pudimos encontrar un perfil con este correo electronico. Verifica que has escrito bien tu correo o Registrate para crear tu perfil.");
                                break;
                        }
                    }
                }
            });
        }
    }

    private void abrirCuadroDeDialogoSucces(String s) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
        dialogBuilder.setMessage(s);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.show();
    }

    private void abrirCuadroDialogoCuentaNoExist(String mensaje){
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setMessage(mensaje);
        materialAlertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        materialAlertDialogBuilder.setPositiveButton("Registrarse", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                *//*startActivity(new Intent(ResetPasswordActivity.this,SingInActivity.class));
                finish();*//*
            }
        });
        materialAlertDialogBuilder.show();
    }

    public boolean isFormularioValido() {
        boolean valido = true;

        String email = editTextEmail.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("Escribe tu correo");
            valido = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Escrbie un correo valido");
            valido = false;
        }

        return valido;
    }*/
}