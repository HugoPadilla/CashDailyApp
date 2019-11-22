package com.wenitech.cashdaily.NewGastoActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.wenitech.cashdaily.Dialog.DatePickerFragment;
import com.wenitech.cashdaily.Dialog.TimePickerFragment;
import com.wenitech.cashdaily.R;

public class NewGastoActivity extends AppCompatActivity implements InterfaceNewGasto.view{

    protected InterfaceNewGasto.presenter presenter;
    private Toolbar toolbar;

    private TextInputEditText edt_valor_gasto, edt_descripcion_gasto;
    private EditText edt_fecha, edt_hora;
    private Button btn_guardar_gasto;
    private TextView tv_guardando_gasto;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gasto);
        presenter = new NewGastoPresenter(this);
        addToolbar();
        addViews();

        edt_valor_gasto = findViewById(R.id.edt_valor_gasto);
        edt_fecha = findViewById(R.id.edt_fecha_gasto);
        edt_hora = findViewById(R.id.edt_hora_gasto);
        edt_descripcion_gasto = findViewById(R.id.edt_descripcion_gasto);

        edt_fecha.setOnClickListener(onClickListener);
        edt_hora.setOnClickListener(onClickListener);

    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbarNewGasto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edt_fecha_gasto:
                    showDatePickerDialog();
                    break;
                case R.id.edt_hora_gasto:
                    showTimePickerDialog();
                            break;
                case R.id.btn_guardar_gasto:
                    GuardatDatos();
                    break;

            }
        }
    };

    private void showTimePickerDialog() {
        TimePickerFragment timePickerFragment = new TimePickerFragment().newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String selectedHora = hourOfDay + ":" + minute;
                edt_hora.setText(selectedHora);
            }
        });
        timePickerFragment.show(getSupportFragmentManager(),"timePicker");
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment().newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String selectedDate = year + "-" + (month+1) + "-" + dayOfMonth;
                edt_fecha.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void addViews() {
        edt_valor_gasto = findViewById(R.id.edt_valor_gasto);
        edt_fecha = findViewById(R.id.edt_fecha_gasto);
        edt_hora = findViewById(R.id.edt_hora_gasto);
        edt_descripcion_gasto = findViewById(R.id.edt_descripcion_gasto);
        btn_guardar_gasto = findViewById(R.id.btn_guardar_gasto);
        btn_guardar_gasto.setOnClickListener(onClickListener);
        tv_guardando_gasto = findViewById(R.id.tv_guardando);
        progressBar = findViewById(R.id.progressBar_new_gasto);
    }

    private void addToolbat() {
        Toolbar toolbar =   findViewById(R.id.toolbarNewGasto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void GuardatDatos() {
        if (!IsFormValid()){
            return;
        }else {
            presenter.GuardarDatos(edt_valor_gasto.getText().toString().trim(),edt_fecha.getText().toString().trim()
                    ,edt_hora.getText().toString().trim(),edt_descripcion_gasto.getText().toString().trim());
        }
    }

    @Override
    public boolean IsFormValid() {
        boolean valid = true;
        String valor = edt_valor_gasto.getText().toString();
        String fecha = edt_fecha.getText().toString();
        String hora = edt_hora.getText().toString();
        String descripcion = edt_descripcion_gasto.getText().toString();

        if (TextUtils.isEmpty(valor)){
            edt_valor_gasto.setError("Ingresa un valor");
            valid = false;
        }else if (TextUtils.isEmpty(fecha)){
            edt_fecha.setError("Seleciona una fecha valida");
            valid = false;
        }else if (TextUtils.isEmpty(hora)){
            edt_hora.setError("Seleciona una hora");
            valid = false;
        }else if (TextUtils.isEmpty(descripcion)){
            edt_descripcion_gasto.setError("Agrega una breve descripcion");
            valid = false;
        }

        return valid;
    }

    @Override
    public void ShowPrograsBar() {
        edt_valor_gasto.setVisibility(View.GONE);
        edt_fecha.setVisibility(View.GONE);
        edt_hora.setVisibility(View.GONE);
        edt_descripcion_gasto.setVisibility(View.GONE);
        btn_guardar_gasto.setVisibility(View.GONE);
        btn_guardar_gasto.setVisibility(View.GONE);
        tv_guardando_gasto.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void HidenProgresBar() {
        tv_guardando_gasto.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        edt_valor_gasto.setVisibility(View.VISIBLE);
        edt_fecha.setVisibility(View.VISIBLE);
        edt_hora.setVisibility(View.VISIBLE);
        edt_descripcion_gasto.setVisibility(View.VISIBLE);
        btn_guardar_gasto.setVisibility(View.VISIBLE);
        btn_guardar_gasto.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnSucess() {
        Toast.makeText(this, edt_fecha.getText().toString()+" "+ edt_hora.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnError() {
        Toast.makeText(this, "Los datos no ha sido guardado", Toast.LENGTH_SHORT).show();
    }
}
