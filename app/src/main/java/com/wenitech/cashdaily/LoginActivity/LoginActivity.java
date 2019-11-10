package com.wenitech.cashdaily.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.wenitech.cashdaily.MainActivity.MainActivity;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.SingInActivity.SingInActivity;

public class LoginActivity extends AppCompatActivity implements InterfaceLoginActivity.view{

    InterfaceLoginActivity.presenter presenter;

    private Button bt_ingresar, bt_create_account;
    private LinearLayout FormLogin;
    private TextInputEditText edt_email, edt_password;
    private TextView tv_ingresando;
    private ProgressBar progressBarLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginActivityPresenter(this);

        bt_ingresar = findViewById(R.id.button_ingresar);
        bt_create_account = findViewById(R.id.button_create_account);

        FormLogin = findViewById(R.id.form_login);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);

        tv_ingresando = findViewById(R.id.tv_ingresando);
        progressBarLogin = findViewById(R.id.progressBarLogin);

        bt_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BotonIngresarPress();
            }
        });
        bt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BotonCrearCuentaPress();
            }
        });
    }

    @Override
    public void BotonIngresarPress() {
        if (!FormValido()){
            return;
        }else {
            presenter.IniciarSesion(edt_email.getText().toString().trim(),edt_password.getText().toString().trim());
        }
    }

    @Override
    public void BotonCrearCuentaPress() {
        Intent intent = new Intent(LoginActivity.this, SingInActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean FormValido() {
        boolean valid = true;
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            edt_email.setError("Ingresa un Correo");
            valid = false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edt_email.setError("No es un correo valido");
            valid = false;
        }else if(TextUtils.isEmpty(password)){
            edt_password.setError("Ingresa una Contraseña");
            valid = false;
        }else if (password.length() < 8){
            edt_password.setError("Minimo 8 caracteres");
            valid = false;
        }

        return valid;
    }

    @Override
    public void ShowFormLogin() {
        FormLogin.setVisibility(View.VISIBLE);
        progressBarLogin.setVisibility(View.INVISIBLE);
        tv_ingresando.setVisibility(View.INVISIBLE);
        bt_ingresar.setEnabled(true);
        bt_create_account.setEnabled(true);
    }

    @Override
    public void HidenFormLogin() {
        FormLogin.setVisibility(View.GONE);
        progressBarLogin.setVisibility(View.VISIBLE);
        tv_ingresando.setVisibility(View.VISIBLE);
        bt_ingresar.setEnabled(false);
        bt_create_account.setEnabled(false);
    }

    @Override
    public void updatesUi() {
        Intent intent =new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void OnErro() {

    }


}
