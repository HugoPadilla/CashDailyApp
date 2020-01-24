package com.wenitech.cashdaily.ActivityLogin.ActivityRegistrarse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wenitech.cashdaily.ActivityLogin.ActivityIniciarSesion.LoginActivity;
import com.wenitech.cashdaily.R;

public class SingInActivity extends AppCompatActivity implements InterfaceSingIn.view, View.OnClickListener {

    private InterfaceSingIn.presenter presenter;
    private Toolbar toolbar;
    private TextInputLayout textInputLayoutNombre, textInputLayoutTipo, textInputLayoutCorreo, textInputLayoutConfirmarCorreo,textInputLayoutContraseña;
    private TextInputEditText editTextNombre, editTextEmail, editTextConfirmarEmail, editTextContraseña;
    private AutoCompleteTextView editTextTipo;
    private MaterialButton buttonCrearCuenta;
    private CheckBox checkBoxTerminos;
    private TextView textViewIniciarSesion;
    private TextView textViewCreandoCuenta;
    private TextView textViewTerminos;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        presenter = new SingInPresenter(this);
        configurarToolbar();
        configurarCasting();
        configurarAutoComple();
    }

    private void configurarAutoComple() {
        String [] opciones = new String[]{"Administrador", "Cobrador"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.item_dropdown,opciones);
        editTextTipo.setAdapter(adapter);
    }

    private void configurarCasting() {

        // Todo: casting of input layout
        textInputLayoutNombre = findViewById(R.id.text_input_layout_crear_cuenta_nombre);
        textInputLayoutTipo = findViewById(R.id.text_input_layout_crear_cuenta_tipo);
        textInputLayoutCorreo = findViewById(R.id.text_input_layout_crear_cuenta_correo);
        textInputLayoutConfirmarCorreo = findViewById(R.id.text_input_layout_confirmar_correo);
        textInputLayoutContraseña = findViewById(R.id.text_input_layout_crear_cuenta_contraseña);

        // Todo casting of edit text
        editTextNombre = findViewById(R.id.edit_text_crear_cuenta_nombre);
        editTextTipo = findViewById(R.id.auto_complete_text_crear_cuenta_tipo);
        editTextEmail = findViewById(R.id.edit_text_crear_cuenta_correo);
        editTextConfirmarEmail = findViewById(R.id.edit_text_crear_cuenta_confirmar_correo);
        editTextContraseña = findViewById(R.id.edit_text_crear_cuenta_contraseña);

        // Todo: casting of buttom and text view buttom
        buttonCrearCuenta = findViewById(R.id.button_crear_cuenta_crear_cuenta);
        textViewTerminos = findViewById(R.id.text_view_crear_cuenta_terminos);
        textViewIniciarSesion = findViewById(R.id.text_view_crear_cuenta_iniciar_sesion);

        // Todo: configurar clic listener
        buttonCrearCuenta.setOnClickListener(this);
        textViewTerminos.setOnClickListener(this);
        textViewIniciarSesion.setOnClickListener(this);

        checkBoxTerminos = findViewById(R.id.check_box_crear_cuenta_terminos);

        // Todo: pregresbar and tex view
        textViewCreandoCuenta = findViewById(R.id.tv_creando_cuenta);
        progressBar = findViewById(R.id.progressBar_singin);
    }

    private void configurarToolbar() {
        toolbar = findViewById(R.id.toolbar_crear_cuenta);
        toolbar.setTitle("Crear una cuenta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void crearCuenta() {
        if (!isFormValid()) {
            return;
        } else {
            String nombreUsuario = editTextNombre.getText().toString().trim();
            String tipoCuenta = editTextTipo.getText().toString().trim();
            String correo = editTextEmail.getText().toString().trim();
            String contraseña = editTextContraseña.getText().toString().trim();
            presenter.CrearCuenta(nombreUsuario, tipoCuenta,correo,contraseña);
        }
    }

    @Override
    public boolean isFormValid() {
        boolean valid = true;
        String nombreUsuario = editTextNombre.getText().toString().trim();
        String tipoCuenta = editTextTipo.getText().toString().trim();
        String correo = editTextEmail.getText().toString().trim();
        String confirmarCorreo = editTextConfirmarEmail.getText().toString().trim();
        String contraseña = editTextContraseña.getText().toString().trim();

        if (TextUtils.isEmpty(nombreUsuario)) {
            editTextNombre.setError("Ingresa un nombre de usuario");
            valid = false;
        } if (TextUtils.isEmpty(tipoCuenta)){
            textInputLayoutTipo.setHelperText("Elige una opción");
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            editTextEmail.setError("No es un correo valido");
            valid = false;
        } else if (TextUtils.isEmpty(confirmarCorreo)) {
            editTextConfirmarEmail.setError("Confirma tu correo");
            valid = false;
        } else if (!TextUtils.equals(correo, confirmarCorreo)) {
            editTextEmail.setError("Los correo no coinciden");
            editTextConfirmarEmail.setText("");
            valid = false;
        } else if (TextUtils.isEmpty(contraseña)) {
            editTextContraseña.setError("Ingresa una Contraseña");
            valid = false;
        } else if (contraseña.length() < 8) {
            editTextContraseña.setError("Debe contener minimo 8 caracteres");
            valid = false;
        } else if (!checkBoxTerminos.isChecked()){
            Toast.makeText(this, "Acepta los terminos y condiciones", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    @Override
    public void ShowFormulario() {

        // Todo: mostrar formulario
        textInputLayoutNombre.setVisibility(View.VISIBLE);
        textInputLayoutTipo.setVisibility(View.VISIBLE);
        textInputLayoutCorreo.setVisibility(View.VISIBLE);
        textInputLayoutConfirmarCorreo.setVisibility(View.VISIBLE);
        textInputLayoutContraseña.setVisibility(View.VISIBLE);

        checkBoxTerminos.setVisibility(View.VISIBLE);

        buttonCrearCuenta.setVisibility(View.VISIBLE);
        textViewTerminos.setVisibility(View.VISIBLE);
        textViewIniciarSesion.setVisibility(View.VISIBLE);

        // Todo: ocultar progresbar
        textViewCreandoCuenta.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void HidenFormulario() {
        // Todo: mostrar formulario
        textInputLayoutNombre.setVisibility(View.GONE);
        textInputLayoutTipo.setVisibility(View.GONE);
        textInputLayoutCorreo.setVisibility(View.GONE);
        textInputLayoutConfirmarCorreo.setVisibility(View.GONE);
        textInputLayoutContraseña.setVisibility(View.GONE);

        checkBoxTerminos.setVisibility(View.GONE);

        buttonCrearCuenta.setVisibility(View.GONE);
        textViewTerminos.setVisibility(View.GONE);
        textViewIniciarSesion.setVisibility(View.GONE);

        // Todo: ocultar progresbar
        textViewCreandoCuenta.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
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
        Toast.makeText(this, "No se pudo crear tu cuenta, intenta nuevamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_crear_cuenta_crear_cuenta:
                crearCuenta();
                break;
            case R.id.text_view_crear_cuenta_terminos:
                break;
            case R.id.text_view_crear_cuenta_iniciar_sesion:
                Intent intent = new Intent(SingInActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }
}
