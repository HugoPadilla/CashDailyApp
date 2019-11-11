package com.wenitech.cashdaily.NewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.wenitech.cashdaily.R;

public class NewClientActivity extends AppCompatActivity implements Interface.view {

    private Interface.presenter presenter;

    private Button button;
    private ProgressBar progressBar;
    private TextView tv_agregando_datos;
    private Toolbar toolbar;
    private CardView cvDatosPersonales, cvDatosContacto;

    private EditText et_fecha;
    private TextInputEditText et_nombre, et_identificacion, et_direcion, et_telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);
        presenter = new NewClientPresenter(this);
        progressBar = findViewById(R.id.progressBar);
        tv_agregando_datos = findViewById(R.id.tv_agregando_datos);
        AddToolbard();
        AddViewEditText();

        button = findViewById(R.id.bt_guardar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicarRegistro();
            }
        });
    }

    private void AddViewEditText() {
        cvDatosPersonales = findViewById(R.id.cardViewDpersonal);
        cvDatosContacto = findViewById(R.id.cardViewDcontact);

        et_nombre = findViewById(R.id.et_nombre_client);
        et_identificacion = findViewById(R.id.et_numero_identificacion);
        et_direcion = findViewById(R.id.et_direccion);
        et_telefono = findViewById(R.id.et_telefono);
    }

    private void AddToolbard() {
        toolbar = findViewById(R.id.toolbarNewGasto);
        toolbar.setTitle("Nuevo Cliente");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void hidenform() {
        cvDatosPersonales.setVisibility(View.GONE);
        cvDatosContacto.setVisibility(View.GONE);
    }

    @Override
    public void showform() {
        cvDatosPersonales.setVisibility(View.VISIBLE);
        cvDatosContacto.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPrgres() {
        progressBar.setVisibility(View.VISIBLE);
        tv_agregando_datos.setVisibility(View.VISIBLE);
        button.setEnabled(false);
    }

    @Override
    public void hidenProgres() {
        progressBar.setVisibility(View.GONE);
        tv_agregando_datos.setVisibility(View.GONE);
        button.setEnabled(true);
    }

    @Override
    public void inicarRegistro() {
        if (!isFormularioValido()){
            return;
        }else {
            String nombre = et_nombre.getText().toString().trim();
            String identificacion = et_identificacion.getText().toString().trim();
            String ubicacion = et_direcion.getText().toString();
            String telefono = et_telefono.getText().toString().trim();

            presenter.presentarRegistro(identificacion,nombre,"",telefono,ubicacion,0,0,true);
        }

    }

    @Override
    public boolean isFormularioValido() {
        boolean valid = true;
        if (TextUtils.isEmpty(et_nombre.getText())){
            valid = false;
        }else if (TextUtils.isEmpty(et_identificacion.getText())){
            valid = false;
        }else if (TextUtils.isEmpty(et_direcion.getText())){
            valid = false;
        }else if (TextUtils.isEmpty(et_telefono.getText())){
            valid = false;
        }
        return valid;
    }

    @Override
    public void onSuces() {
        Toast.makeText(this, "Cliente Agregado", Toast.LENGTH_SHORT).show();
        finishAfterTransition();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "No se pudo guardar los datos", Toast.LENGTH_SHORT).show();
    }
}
