package com.wenitech.cashdaily.ui.Main.ActivityClientes.ActivityNuevoCliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wenitech.cashdaily.R;

public class NewClientActivity extends AppCompatActivity implements Interface.view, View.OnClickListener {
    private Interface.presenter presenter;

    private MaterialButton buttonGuardarCliente;
    private ProgressBar progressBar;
    private TextView textViewAgregando, textViewInformacionPersonal, textViewInformacionConcacto;
    private Toolbar toolbarNuevoCliente;
    private TextInputEditText editTextNombreCliente, editTextIdentificacion, editTextTelefono, editTextCiudad, editTextDireccion;
    private AutoCompleteTextView autoCompleteTextViewGenero;

    private TextInputLayout textInputLayoutNombre,textInputLayoutIdentificacion, textInputLayoutGenero ,textInputLayoutTelefono,textInputLayoutCiudad,textInputLayoutDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);
        presenter = new NewClientPresenter(this);

        configurarToolbar();
        castingViewAndClikListener();
    }

    private void castingViewAndClikListener() {
        buttonGuardarCliente = findViewById(R.id.button_guardar_cliente);
        buttonGuardarCliente.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        textViewAgregando = findViewById(R.id.text_view_agregando);
        textViewInformacionPersonal = findViewById(R.id.text_view_new_cliente_info_personal);
        textViewInformacionConcacto = findViewById(R.id.text_view_nuevo_cliente_detalles_contacto);

        textInputLayoutNombre = findViewById(R.id.textInputLayout3);
        textInputLayoutIdentificacion = findViewById(R.id.textInputLayout8);
        textInputLayoutGenero = findViewById(R.id.textInputLayout9);
        textInputLayoutTelefono = findViewById(R.id.textInputLayout10);
        textInputLayoutCiudad = findViewById(R.id.textInputLayout11);
        textInputLayoutDireccion = findViewById(R.id.textInputLayout12);

        editTextNombreCliente = findViewById(R.id.edit_text_nombre_nuevo_cliente);
        editTextIdentificacion = findViewById(R.id.edit_text_nuevo_cliente_identificacion);
        autoCompleteTextViewGenero = findViewById(R.id.edit_text_nuevo_cliente_genero);
        configurarAdapterAutoCompleEditText();
        editTextTelefono = findViewById(R.id.edit_text_nuevo_cliente_telefono);
        editTextCiudad = findViewById(R.id.edit_text_nuevo_cliente_ciudad);
        editTextDireccion = findViewById(R.id.edit_text_nuevo_cliente_direccion);
    }

    private void configurarAdapterAutoCompleEditText() {
        String [] opcionesGenero = new String[]{"Mujer","Hombre","No espesificar","Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.item_dropdown,opcionesGenero);
        autoCompleteTextViewGenero.setAdapter(adapter);
    }

    private void configurarToolbar() {
        toolbarNuevoCliente = findViewById(R.id.toolbard_nuevo_cliente);
        toolbarNuevoCliente.setTitle("Nuevo Cliente");
        setSupportActionBar(toolbarNuevoCliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void hidenForm() {
        buttonGuardarCliente.setVisibility(View.GONE);
        textViewInformacionPersonal.setVisibility(View.GONE);
        textViewInformacionConcacto.setVisibility(View.GONE);

        textInputLayoutNombre.setVisibility(View.GONE);
        textInputLayoutIdentificacion.setVisibility(View.GONE);
        textInputLayoutGenero.setVisibility(View.GONE);
        textInputLayoutTelefono.setVisibility(View.GONE);
        textInputLayoutCiudad.setVisibility(View.GONE);
        textInputLayoutDireccion.setVisibility(View.GONE);

    }

    @Override
    public void showForm() {
        buttonGuardarCliente.setVisibility(View.VISIBLE);
        textViewInformacionPersonal.setVisibility(View.VISIBLE);
        textViewInformacionConcacto.setVisibility(View.VISIBLE);

        textInputLayoutNombre.setVisibility(View.VISIBLE);
        textInputLayoutIdentificacion.setVisibility(View.VISIBLE);
        textInputLayoutGenero.setVisibility(View.VISIBLE);
        textInputLayoutTelefono.setVisibility(View.VISIBLE);
        textInputLayoutCiudad.setVisibility(View.VISIBLE);
        textInputLayoutDireccion.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgres() {
        progressBar.setVisibility(View.VISIBLE);
        textViewAgregando.setVisibility(View.VISIBLE);
        buttonGuardarCliente.setEnabled(false);
    }

    @Override
    public void hidenProgres() {
        progressBar.setVisibility(View.GONE);
        textViewAgregando.setVisibility(View.GONE);
        buttonGuardarCliente.setEnabled(true);
    }

    @Override
    public void iniciarRegistro() {
        if (!isFormularioValid()){
            return;
        }else {
            String nombre = editTextNombreCliente.getText().toString().trim();
            String identificacion = editTextIdentificacion.getText().toString().trim();
            String genero = autoCompleteTextViewGenero.getText().toString().trim();
            String telefono = editTextTelefono.getText().toString().trim();
            String ciudad = editTextCiudad.getText().toString().trim();
            String direcion = editTextDireccion.getText().toString();

            presenter.presentarRegistro(nombre,identificacion,genero,telefono,ciudad,direcion);
        }

    }

    @Override
    public boolean isFormularioValid() {
        boolean valid = true;
        if (TextUtils.isEmpty(editTextNombreCliente.getText())){
            editTextNombreCliente.setError("Escribe un nombre");
            valid = false;
        }else if (TextUtils.isEmpty(editTextIdentificacion.getText())){
            editTextIdentificacion.setError("Identificacion del cliente");
            valid = false;
        }else if (TextUtils.isEmpty(autoCompleteTextViewGenero.getText())){
            autoCompleteTextViewGenero.setError("Elige una opcion");
            valid = false;
        }else if (TextUtils.isEmpty(editTextTelefono.getText())){
            editTextTelefono.setError("Escribe un numero de contacto");
            valid = false;
        }else if (TextUtils.isEmpty(editTextCiudad.getText())){
            editTextCiudad.setError("Agrega un ciudad");
            valid = false;
        }else if (TextUtils.isEmpty(editTextDireccion.getText())){
            editTextDireccion.setError("Direcion de residencia");
            valid = false;
        }
        return valid;
    }

    @Override
    public void onSucces() {
        Toast.makeText(this, "Cliente Agregado", Toast.LENGTH_SHORT).show();
        finishAfterTransition();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "No se pudo guardar los datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_guardar_cliente){
            iniciarRegistro();
        }
    }

}
