package com.wenitech.cashdaily.SingInActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.LoginActivity.LoginActivity;
import com.wenitech.cashdaily.MainActivity.MainActivity;
import com.wenitech.cashdaily.R;

public class SingInActivity extends AppCompatActivity implements InterfaceSingIn.view {

    private InterfaceSingIn.presenter presenter;
    private Toolbar toolbar;
    private ScrollView formCrearCuenta;
    private LinearLayout form_login;
    private TextInputEditText edt_userName, edt_correo, edt_password, edt_password_confirm;
    private Button bt_singin;
    private TextView tv_Login;
    private TextView tv_creando_cuenta;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        presenter = new SingInPresenter(this);
        toolbar = findViewById(R.id.toolbarSingIn);
        toolbar.setTitle("Crear cuenta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        formCrearCuenta = findViewById(R.id.form_sing);
        form_login = findViewById(R.id.form_login);

        edt_userName = findViewById(R.id.edt_user_name);
        edt_correo = findViewById(R.id.edt_email_singin);
        edt_password = findViewById(R.id.edt_password_singin);
        edt_password_confirm = findViewById(R.id.edt_confirn_password_singin);

        bt_singin = findViewById(R.id.bt_sing_in);
        bt_singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCuenta();
            }
        });
        tv_Login = findViewById(R.id.tv_Login);
        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingInActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        tv_creando_cuenta = findViewById(R.id.tv_creando_cuenta);
        progressBar = findViewById(R.id.progressBar_singin);
    }

    @Override
    public void crearCuenta() {

        if (!isFormValid()) {
            return;
        } else {
            presenter.CrearCuenta(edt_userName.getText().toString().trim(), edt_correo.getText().toString().trim(),
                    edt_password.getText().toString().trim());
        }
    }

    @Override
    public boolean isFormValid() {
        boolean valid = true;
        String userName = edt_userName.getText().toString().trim();
        String correo = edt_correo.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String passconfir = edt_password_confirm.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            edt_userName.setError("Ingresa un nombre de usuario");
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            edt_correo.setError("No es un correo valido");
            valid = false;
        } else if (TextUtils.isEmpty(password)) {
            edt_password.setError("Ingresa una Contraseña");
            valid = false;
        } else if (password.length() < 8) {
            edt_password.setError("Debe contener minimo 8 caracteres");
            valid = false;
        } else if (TextUtils.isEmpty(passconfir)) {
            edt_password_confirm.setError("Confirma la Contraseña");
            valid = false;
        } else if (!TextUtils.equals(password, passconfir)) {
            edt_password.setError("Las contraseñas no coinciden");
            edt_password_confirm.setText("");
            valid = false;
        }

        return valid;
    }

    @Override
    public void ShowFormulario() {
        formCrearCuenta.setVisibility(View.VISIBLE);
        form_login.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tv_creando_cuenta.setVisibility(View.GONE);
    }

    @Override
    public void HidenFormulario() {
        formCrearCuenta.setVisibility(View.GONE);
        form_login.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tv_creando_cuenta.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSucess(String emailSucess) {
        Toast.makeText(this, "Su nueva Cuenta se creo con exito", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SingInActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onExist() {
        Toast.makeText(this, "Ya existe una cuenta con este correo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "No se puedo crear la cuenta, intenta nuevamente", Toast.LENGTH_SHORT).show();
    }
}
