package com.wenitech.cashdaily.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.wenitech.cashdaily.MainActivity.MainActivity;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.ActivityResetPassword.ResetPasswordActivity;
import com.wenitech.cashdaily.SingInActivity.SingInActivity;

public class LoginActivity extends AppCompatActivity implements InterfaceLoginActivity.view {

    InterfaceLoginActivity.presenter presenter;

    private Button botonIniciarSesion;
    private TextView textViewResetPassword, textViewRegistrarse;
    private ScrollView FormLogin;
    private TextInputEditText editTextEmail, editTextPassword;
    private TextView tv_ingresando;
    private ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginActivityPresenter(this);

        botonIniciarSesion = findViewById(R.id.boton_iniciar_sesion);
        textViewResetPassword = findViewById(R.id.textview_reset_password);
        textViewRegistrarse = findViewById(R.id.textview_registrarse);

        FormLogin = findViewById(R.id.form_login);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);

        tv_ingresando = findViewById(R.id.tv_ingresando);
        progressBarLogin = findViewById(R.id.progressBarLogin);

        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        textViewResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irActivityResetPassword();
            }
        });

        textViewRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivityRegistrarse();
            }
        });

    }

    private void irActivityResetPassword() {
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        // Configurar Transision
        startActivity(intent);
    }

    @Override
    public void iniciarSesion() {
        if (!FormValido()) {
            return;
        } else {
            presenter.IniciarSesion(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim());
        }
    }

    @Override
    public void goActivityRegistrarse() {
        Intent intent = new Intent(LoginActivity.this, SingInActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean FormValido() {
        boolean valid = true;
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Ingresa un Correo");
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("No es un correo valido");
            valid = false;
        } else if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Ingresa una Contraseña");
            valid = false;
        } else if (password.length() < 8) {
            editTextPassword.setError("Minimo 8 caracteres");
            valid = false;
        }

        return valid;
    }

    @Override
    public void ShowFormLogin() {
        FormLogin.setVisibility(View.VISIBLE);
        progressBarLogin.setVisibility(View.INVISIBLE);
        tv_ingresando.setVisibility(View.INVISIBLE);
        textViewRegistrarse.setEnabled(true);
    }

    @Override
    public void HidenFormLogin() {
        FormLogin.setVisibility(View.GONE);
        progressBarLogin.setVisibility(View.VISIBLE);
        tv_ingresando.setVisibility(View.VISIBLE);
        textViewRegistrarse.setEnabled(false);
    }

    @Override
    public void updatesUi() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void OnErro() {
        Toast.makeText(this, "Correo o contraseña incorectos", Toast.LENGTH_SHORT).show();
    }
}
