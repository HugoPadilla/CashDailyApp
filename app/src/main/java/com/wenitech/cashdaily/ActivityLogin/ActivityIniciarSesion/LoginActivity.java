package com.wenitech.cashdaily.ActivityLogin.ActivityIniciarSesion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wenitech.cashdaily.ActivityMain.MainActivity;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.ActivityLogin.ActivityRecuperaContraseña.ResetPasswordActivity;
import com.wenitech.cashdaily.ActivityLogin.ActivityRegistrarse.SingInActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, InterfaceLoginActivity.view {

    InterfaceLoginActivity.presenter presenter;

    private ImageView imageViewLogo;
    private TextView textViewLogo, textViewResetPassword, textViewO, textViewIngresando;
    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    private TextInputEditText editTextEmail, editTextPassword;
    private AppCompatButton buttonIniciarSesion;
    private MaterialButton materialButtonRegister;
    private View viewDivisorIzquierdo, viewDivisorDerecho;
    private ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginActivityPresenter(this);
        castingCompnent();
        settingOnClick();
    }

    private void settingOnClick() {
        textViewResetPassword.setOnClickListener(this);
        buttonIniciarSesion.setOnClickListener(this);
        materialButtonRegister.setOnClickListener(this);
    }

    private void castingCompnent() {
        imageViewLogo = findViewById(R.id.image_view_logo);
        textViewLogo = findViewById(R.id.text_view_logo);
        textViewResetPassword = findViewById(R.id.textview_reset_password);
        textViewO = findViewById(R.id.text_view_o);
        textViewIngresando = findViewById(R.id.text_view_ingresando);
        textInputLayoutEmail = findViewById(R.id.text_input_layout_email);
        textInputLayoutPassword = findViewById(R.id.text_input_layout_password);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonIniciarSesion = findViewById(R.id.button_iniciar_sesion);
        materialButtonRegister = findViewById(R.id.material_button_register);
        viewDivisorIzquierdo = findViewById(R.id.view_divider_izquierdo);
        viewDivisorDerecho = findViewById(R.id.view_divider_derecho);
        progressBarLogin = findViewById(R.id.progress_bar_Login);

    }


    @Override
    public void iniciarSesion() {
        if (!FormValido()) {
            return;
        } else {
            presenter.IniciarSesion(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim());
        }
    }

    private void irActivityResetPassword() {
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        // Configurar Transision
        startActivity(intent);
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
        imageViewLogo.setVisibility(View.VISIBLE);
        textViewLogo.setVisibility(View.VISIBLE);
        textViewResetPassword.setVisibility(View.VISIBLE);
        textViewO.setVisibility(View.VISIBLE);
        textViewIngresando.setVisibility(View.GONE);
        textInputLayoutEmail.setVisibility(View.VISIBLE);
        textInputLayoutPassword.setVisibility(View.VISIBLE);
        editTextEmail.setVisibility(View.VISIBLE);
        editTextPassword.setVisibility(View.VISIBLE);
        buttonIniciarSesion.setVisibility(View.VISIBLE);
        materialButtonRegister.setVisibility(View.VISIBLE);
        viewDivisorIzquierdo.setVisibility(View.VISIBLE);
        viewDivisorDerecho.setVisibility(View.VISIBLE);
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void HidenFormLogin() {
        imageViewLogo.setVisibility(View.GONE);
        textViewLogo.setVisibility(View.GONE);
        textViewResetPassword.setVisibility(View.GONE);
        textViewO.setVisibility(View.GONE);
        textViewIngresando.setVisibility(View.VISIBLE);
        textInputLayoutEmail.setVisibility(View.GONE);
        textInputLayoutPassword.setVisibility(View.GONE);
        editTextEmail.setVisibility(View.GONE);
        editTextPassword.setVisibility(View.GONE);
        buttonIniciarSesion.setVisibility(View.GONE);
        materialButtonRegister.setVisibility(View.GONE);
        viewDivisorIzquierdo.setVisibility(View.GONE);
        viewDivisorDerecho.setVisibility(View.GONE);
        progressBarLogin.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textview_reset_password:
                irActivityResetPassword();
                break;
            case R.id.button_iniciar_sesion:
                iniciarSesion();
                break;
            case R.id.material_button_register:
                goActivityRegistrarse();
                break;
        }
    }
}
