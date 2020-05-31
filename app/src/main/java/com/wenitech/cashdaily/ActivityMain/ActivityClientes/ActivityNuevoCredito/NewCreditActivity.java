package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityNuevoCredito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wenitech.cashdaily.common.pojo.Credito;
import com.wenitech.cashdaily.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewCreditActivity extends AppCompatActivity implements View.OnClickListener, NewCreditoInterface.view {
    private NewCreditoInterface.presente presente;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReferenceCredito;

    private String REFERENCIA_DOCUMENTO_CLIENTE;
    private Toolbar toolbar;

    // Todo: views Formulario informacion pretamo
    private TextInputEditText editTextFecha;
    private TextInputEditText editTextHora;
    private TextInputEditText editTextValorPrestamo;
    private AutoCompleteTextView autoCompleteTextViewPorcentaje;
    private TextInputEditText editTextTotalCredito;

    private TextInputLayout
            textInputLayoutFecha,
            textInputLayoutHora,
            textInputLayoutValorPrestamo,
            textInputLayoutPorcentaje,
            textInputLayoutTotalCredito;

    // Todo: formulario forma de cobro
    private RadioGroup radioGroupModalidad;
    private TextInputLayout
            textInputLayoutPlazo,
            textInputLayoutValorCuota;
    private TextInputEditText
            editTextPlazo,
            editTextValorCuota;
    private TextView textViewNoCobrar;
    private Switch
            aSwitchSabado,
            aSwitchDomingo;

    private ConstraintLayout constraintLayoutFormulario;

    private ProgressBar progressBar;

    private ExtendedFloatingActionButton floatingActionButtonGuardar;

    private Credito mCredito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presente = new NewCreditoPresenter(this);
        setContentView(R.layout.activity_new_credit);
        configurarToolbar();
        castingView();
        addListenerView();
        configurarInicioEdittext();
        configurarAutocompletPorcentaje();
        REFERENCIA_DOCUMENTO_CLIENTE = getIntent().getStringExtra("REFERENCIA_CLIENTE");
    }

    private void configurarAutocompletPorcentaje() {
        String [] opciones = new String[]{"00","03","05","10","15","20","30"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.item_dropdown,opciones);
        autoCompleteTextViewPorcentaje.setAdapter(adapter);
    }

    private void configurarToolbar() {
        toolbar = findViewById(R.id.toolbar_credito_vacio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void castingView() {
        // casting view informacion de prestamo
        textInputLayoutFecha = findViewById(R.id.text_input_layout_nuevo_credito_fecha);
        textInputLayoutHora = findViewById(R.id.text_input_layout_nuevo_credito_hora);
        textInputLayoutValorPrestamo = findViewById(R.id.text_input_layout_nuevo_credito_valor_prestamo);
        textInputLayoutPorcentaje = findViewById(R.id.text_input_layout_nuevo_credito_porcentaje);
        textInputLayoutTotalCredito = findViewById(R.id.text_input_layout_nuevo_credito_total_credito);

        editTextFecha = findViewById(R.id.edit_text_nuevo_credito_fecha);
        editTextHora = findViewById(R.id.edit_text_nuevo_credito_hora);
        editTextValorPrestamo = findViewById(R.id.edit_text_nuevo_credito_valor_prestamo);
        autoCompleteTextViewPorcentaje = findViewById(R.id.auto_complete_text_nuevo_credito_porcentaje);
        editTextTotalCredito = findViewById(R.id.edit_text_nuevo_credito_total_credito);

        // casting forma de cobro
        radioGroupModalidad = findViewById(R.id.radio_group_nuevo_credito_modalidad);
        textInputLayoutPlazo = findViewById(R.id.text_input_layout_nuevo_credito_plazo);
        textInputLayoutValorCuota = findViewById(R.id.text_input_layout_nuevo_credito_valor_cuota);

        editTextPlazo = findViewById(R.id.edit_text_nuevo_credito_plazo);
        editTextValorCuota = findViewById(R.id.edit_text_nuevo_credito_valor_cuota);
        textViewNoCobrar = findViewById(R.id.text_view_no_cobrar_dias);
        aSwitchSabado = findViewById(R.id.switch_nuevo_credito_sabados);
        aSwitchDomingo = findViewById(R.id.switch_nuevo_credito_domingo);

        constraintLayoutFormulario = findViewById(R.id.constrain_layout_formulario);
        progressBar = findViewById(R.id.progress_bar_nuevo_credito);

        floatingActionButtonGuardar = findViewById(R.id.fab_button_nuevo_credito_guardar);
    }

    private void addListenerView() {
        radioGroupModalidad.setOnCheckedChangeListener(onCheckedChangeListener);
        floatingActionButtonGuardar.setOnClickListener(this);
    }

    private void configurarInicioEdittext() {
        Date date = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd/mm/yyy");
        SimpleDateFormat formatHora = new SimpleDateFormat("hh:mm a");
        String fecha = formatFecha.format(date);
        String hora = formatHora.format(date);
        editTextFecha.setText(fecha);
        editTextHora.setText(hora);
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radio_button_nuevo_credito_diario:
                    if (isFormularioCreditoValido()) {
                        textInputLayoutPlazo.setVisibility(View.VISIBLE);
                        textInputLayoutValorCuota.setVisibility(View.VISIBLE);
                        textInputLayoutPlazo.setHint("Dias de plazo");
                        textViewNoCobrar.setVisibility(View.VISIBLE);
                        aSwitchSabado.setVisibility(View.VISIBLE);
                        aSwitchDomingo.setVisibility(View.VISIBLE);
                    } else {
                        radioGroupModalidad.clearCheck();
                        return;
                    }
                    break;
                case R.id.radio_button_nuevo_credito_semanal:
                    if (isFormularioCreditoValido()) {
                        textInputLayoutPlazo.setVisibility(View.VISIBLE);
                        textInputLayoutValorCuota.setVisibility(View.VISIBLE);
                        textInputLayoutPlazo.setHint("Semanas de plazo");
                        textViewNoCobrar.setVisibility(View.GONE);
                        aSwitchSabado.setVisibility(View.GONE);
                        aSwitchDomingo.setVisibility(View.GONE);
                    } else {
                        radioGroupModalidad.clearCheck();
                        return;
                    }
                    break;
                case R.id.radio_button_nuevo_credito_quincenal:
                    if (isFormularioCreditoValido()) {
                        textInputLayoutPlazo.setVisibility(View.VISIBLE);
                        textInputLayoutValorCuota.setVisibility(View.VISIBLE);
                        textInputLayoutPlazo.setHint("Quincenas de plazo");
                        textViewNoCobrar.setVisibility(View.GONE);
                        aSwitchSabado.setVisibility(View.GONE);
                        aSwitchDomingo.setVisibility(View.GONE);
                    } else {
                        radioGroupModalidad.clearCheck();
                        return;
                    }
                    break;
                case R.id.radio_button_nuevo_credito_mensual:
                    if (isFormularioCreditoValido()) {
                        textInputLayoutPlazo.setVisibility(View.VISIBLE);
                        textInputLayoutValorCuota.setVisibility(View.VISIBLE);
                        textInputLayoutPlazo.setHint("meses de plazo");
                        textViewNoCobrar.setVisibility(View.GONE);
                        aSwitchSabado.setVisibility(View.GONE);
                        aSwitchDomingo.setVisibility(View.GONE);
                    } else {
                        radioGroupModalidad.clearCheck();
                        return;
                    }
                    break;
            }
        }
    };

    @Override
    public void ocultarView() {
        constraintLayoutFormulario.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void mostrarView() {
        constraintLayoutFormulario.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean isFormularioCreditoValido() {
        boolean valido = true;
        if (TextUtils.isEmpty(editTextFecha.getText().toString())) {
            textInputLayoutFecha.setError("Seleciona una fecha");
            valido = false;
        } else if (TextUtils.isEmpty(editTextHora.getText().toString())) {
            textInputLayoutHora.setError("Establece una hora");
            valido = false;
        } else if (TextUtils.isEmpty(editTextValorPrestamo.getText().toString())) {
            textInputLayoutValorPrestamo.setError("Escribe un valor");
            valido = false;
        } else if (TextUtils.isEmpty(autoCompleteTextViewPorcentaje.getText().toString())) {
            textInputLayoutPorcentaje.setError("Establece un porcentaje");
            valido = false;
        } else if (TextUtils.isEmpty(editTextTotalCredito.getText().toString())) {
            textInputLayoutTotalCredito.setError("Total de credito");
            valido = false;
        }
        return valido;
    }

    @Override
    public boolean isFormularioModalidadValido() {
        boolean isValid = true;
        if (TextUtils.isEmpty(editTextPlazo.getText().toString())) {
            textInputLayoutPlazo.setError("Estable una plazo");
            isValid = false;
        } else if (TextUtils.isEmpty(editTextValorCuota.getText().toString())) {
            textInputLayoutValorCuota.setError("Valor cuota");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onSucces() {
        Toast.makeText(this, "Credito agregado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("MENSAJE",true);
        setResult(0,intent);
        finish();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "No es posible agregar el credito", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void registrarCredito() {
        if (isFormularioCreditoValido() && isFormularioModalidadValido()) {
            mCredito = new Credito();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/mm/yyy hh:mm a");
            String stringFecha = editTextFecha.getText().toString() + " " + editTextHora.getText().toString();
            Date dateFecha = null;
            Timestamp fecha = Timestamp.now();
            try {
                dateFecha = formatoFecha.parse(stringFecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dateFecha != null) {
                fecha = new Timestamp(dateFecha);
                mCredito.setFechaPretamo(fecha);
            }

            String valorPrestamo = editTextValorPrestamo.getText().toString();
            String porcentaje = autoCompleteTextViewPorcentaje.getText().toString();
            String totalCredito = editTextTotalCredito.getText().toString();

            mCredito.setValorPrestamo(Double.parseDouble(valorPrestamo));
            mCredito.setPorcentaje(Double.parseDouble(porcentaje));
            mCredito.setTotalPrestamo(Double.parseDouble(totalCredito));
            mCredito.setDeudaPrestamo(Double.parseDouble(totalCredito));

            switch (radioGroupModalidad.getCheckedRadioButtonId()) {
                case R.id.radio_button_nuevo_credito_diario:
                    mCredito.setModalida("Diario");

                    String plazoDias = editTextPlazo.getText().toString().trim();
                    mCredito.setNumeroCuotas(Double.parseDouble(plazoDias));
                    String valorCuota = editTextValorCuota.getText().toString().trim();
                    mCredito.setValorCuota(Double.parseDouble(valorCuota));

                    mCredito.setNoCobrarSabados(aSwitchSabado.isChecked());
                    mCredito.setNoCobrarDomingos(aSwitchDomingo.isChecked());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateFecha);
                    calendar.add(Calendar.DAY_OF_YEAR,1);

                    mCredito.setFechaProximaCuota(new Timestamp(calendar.getTime()));

                    break;
                case R.id.radio_button_nuevo_credito_semanal:
                    mCredito.setModalida("Semanal");
                    String plazoSemanas = editTextPlazo.getText().toString().trim();
                    mCredito.setNumeroCuotas(Double.parseDouble(plazoSemanas));

                    mCredito.setNoCobrarSabados(false);
                    mCredito.setNoCobrarDomingos(false);
                    break;
                case R.id.radio_button_nuevo_credito_quincenal:
                    mCredito.setModalida("Quincenal");
                    String plazoQuicena = editTextPlazo.getText().toString().trim();
                    mCredito.setNumeroCuotas(Double.parseDouble(plazoQuicena));

                    mCredito.setNoCobrarSabados(false);
                    mCredito.setNoCobrarDomingos(false);
                    break;
                case R.id.radio_button_nuevo_credito_mensual:
                    mCredito.setModalida("Mensual");
                    String plazoMensual = editTextPlazo.getText().toString().trim();
                    mCredito.setNumeroCuotas(Double.parseDouble(plazoMensual));

                    mCredito.setNoCobrarSabados(false);
                    mCredito.setNoCobrarDomingos(false);
                    break;
            }

            presente.registrarCredito(mCredito, REFERENCIA_DOCUMENTO_CLIENTE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_button_nuevo_credito_guardar:
                registrarCredito();
                break;
        }
    }
}
