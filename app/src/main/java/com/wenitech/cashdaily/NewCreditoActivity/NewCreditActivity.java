package com.wenitech.cashdaily.NewCreditoActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wenitech.cashdaily.Dialog.DialogoFormaCobroDiario;
import com.wenitech.cashdaily.Dialog.DialogoFormaCobroMensual;
import com.wenitech.cashdaily.Dialog.DialogoFormaCobroQuincenal;
import com.wenitech.cashdaily.Dialog.DialogoFormaCobroSemanal;
import com.wenitech.cashdaily.Model.Credito;
import com.wenitech.cashdaily.R;

import org.w3c.dom.Text;

public class NewCreditActivity extends AppCompatActivity implements View.OnClickListener, DialogoFormaCobroDiario.DialogoFormaCobroDiarioListener,
        DialogoFormaCobroSemanal.DialogoFormaCobroDiarioListener, DialogoFormaCobroQuincenal.DialogoFormaCobroDiarioListener, DialogoFormaCobroMensual.DialogoFormaCobroDiarioListener {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReferenceCredito;

    private String ID_CLIENTE_REFERENCIA;

    private Toolbar toolbar;
    private TextInputEditText editTextFecha;
    private TextInputEditText editTextHora;
    private TextInputEditText editTextValorPrestamo;
    private TextInputEditText editTextPorcentaje;
    private TextInputEditText editTextTotalPrestamo;

    private RadioGroup radioGroupFormaCobro;

    private Button buttonRealizarPrestamo;

    private Timestamp aFechaCreacion;
    private double bValorPrestamo;
    private int cPorcentaje;
    private double dTotalPrestamo;
    private String eModalida;
    private String fDiaSemanaCobrar;
    private int gDiaQuincenaInicial;
    private int hDiaQuincenaFinal;
    private int iDiaMensual;
    private double jNumeroCuotas;
    private double kValorCuotas;
    private boolean lActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_credit);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ID_CLIENTE_REFERENCIA = getIntent().getStringExtra("ID_CLIENTE_REFERENCIA");

        castingView();
        addListenerView();
    }

    private void addListenerView() {
        buttonRealizarPrestamo.setOnClickListener(this);
    }

    private void castingView() {
        editTextFecha = findViewById(R.id.edit_fecha);
        editTextHora = findViewById(R.id.edit_hora);
        editTextValorPrestamo = findViewById(R.id.edit_valor_prestamo);
        editTextPorcentaje = findViewById(R.id.edit_porcentaje);
        editTextTotalPrestamo = findViewById(R.id.edit_total_prestamo);
        radioGroupFormaCobro = findViewById(R.id.radio_group_forma_cobro);
        buttonRealizarPrestamo = findViewById(R.id.button_realizar_prestamo);
    }

    public void checkRadioButtomOnPresset(View view) {
        int radioButtonid = radioGroupFormaCobro.getCheckedRadioButtonId();
        if (radioButtonid == R.id.radiobutton_diario) {
            if (!validFormulario()){
                return;
            }else {
                opemDialogCobrodiario();
                Toast.makeText(this, "Abrir dialog de pretamo diario", Toast.LENGTH_SHORT).show();
            }
        } else if (radioButtonid == R.id.radiobutton_semanal) {
            if (!validFormulario()){
                return;
            }else {
                openDialogoCobroSemanal();
                Toast.makeText(this, "Abrir dialog de pretamo semanal", Toast.LENGTH_SHORT).show();
            }
        } else if (radioButtonid == R.id.radiobutton_quincenal) {
            if (!validFormulario()){
                return;
            }else {
                openDialogoCobroQuincenal();
                Toast.makeText(this, "Abrir dialog de pretamo quincenal", Toast.LENGTH_SHORT).show();
            }
        } else if (radioButtonid == R.id.radiobutton_mensual) {
            if (!validFormulario()){
                return;
            }else {
                opemDialogCobroMensual();
                Toast.makeText(this, "Abrir dialog de pretamo messual", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void opemDialogCobroMensual() {
        DialogoFormaCobroMensual dialogoFormaCobroMensual = new DialogoFormaCobroMensual(Integer.parseInt(editTextTotalPrestamo.getText().toString()));
        dialogoFormaCobroMensual.show(getSupportFragmentManager(), "Dialogo cobro mensual");
    }

    private void openDialogoCobroQuincenal() {
        DialogoFormaCobroQuincenal dialogoFormaCobroQuincenal = new DialogoFormaCobroQuincenal(Integer.parseInt(editTextTotalPrestamo.getText().toString()));
        dialogoFormaCobroQuincenal.show(getSupportFragmentManager(), "Dialogo cobro quincenal");
    }

    private void openDialogoCobroSemanal() {
        DialogoFormaCobroSemanal dialogoFormaCobroSemanal = new DialogoFormaCobroSemanal(Integer.parseInt(editTextTotalPrestamo.getText().toString()));
        dialogoFormaCobroSemanal.show(getSupportFragmentManager(), "Dialogo cobro semanal");
    }

    private void opemDialogCobrodiario() {
        DialogoFormaCobroDiario dialogoFormaCobroDiario =
                new DialogoFormaCobroDiario(Integer.parseInt(editTextTotalPrestamo.getText().toString()));
        dialogoFormaCobroDiario.show(getSupportFragmentManager(), "Dialogo cobro diario");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button_realizar_prestamo) {
            if (!validFormulario()) {
                return;
            }else {

                // Timestamp aqui
                bValorPrestamo = Integer.parseInt(editTextValorPrestamo.getText().toString());
                cPorcentaje = Integer.parseInt(editTextPorcentaje.getText().toString());
                dTotalPrestamo = Integer.parseInt(editTextTotalPrestamo.getText().toString());


                nuevoCredito();
            }

        }
    }

    public boolean validFormulario() {
        boolean valido = true;

        if (TextUtils.isEmpty(editTextFecha.getText().toString())){
            editTextFecha.setError("Estable una fecha");
            valido = false;
        } else if (TextUtils.isEmpty(editTextHora.getText().toString())){
            valido = false;
            editTextHora.setError("Estable la hora");
        }else if (TextUtils.isEmpty(editTextValorPrestamo.getText().toString())){
            editTextValorPrestamo.setError("Valor del prestamo invalido");
            valido = false;
        }else if (TextUtils.isEmpty(editTextPorcentaje.getText().toString())){
            editTextPorcentaje.setError("Establece un porcentaje");
            valido = false;
        } else if (TextUtils.isEmpty(editTextTotalPrestamo.getText().toString())){
            editTextTotalPrestamo.setError("Total de pretamo");
            valido = false;
        }

        return valido;
    }

    private void nuevoCredito() {

        documentReferenceCredito = db.document(ID_CLIENTE_REFERENCIA).collection("/creditos").document("credito");

        Credito credito = new Credito();
        credito.setaFechaCreacion(Timestamp.now());
        credito.setbValorPrestamo(bValorPrestamo);
        credito.setcPorcentaje(cPorcentaje);
        credito.setdTotalPrestamo(dTotalPrestamo);
        credito.seteModalida(eModalida);
        credito.setfDiaSemanaCobrar(fDiaSemanaCobrar);
        credito.setgDiaQuincenaInicial(gDiaQuincenaInicial);
        credito.sethDiaQuincenaFinal(hDiaQuincenaFinal);
        credito.setiDiaMensual(iDiaMensual);
        credito.setjNumeroCuotas(jNumeroCuotas);
        credito.setkValorCuotas(kValorCuotas);
        credito.setlActivo(true);

        documentReferenceCredito.set(credito).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(NewCreditActivity.this, "Se agrego un nuevo credito", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewCreditActivity.this, "No pudimo guardar este credito", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void aplicarModalidaDiario(double DiasDePlazo, double valorCuota) {
        //Datos del modalida diara
        eModalida = "Diario";
        jNumeroCuotas = DiasDePlazo;
        kValorCuotas = valorCuota;
    }

    @Override
    public void aplicarModalidaSemanal(String diasSemanaCobrar, double SemanasDePlazo, double valorCuota) {
        // Datos de Modalida Semanal
        eModalida = "Semanal";
        fDiaSemanaCobrar = diasSemanaCobrar;
        jNumeroCuotas = SemanasDePlazo;
        kValorCuotas = valorCuota;
    }

    @Override
    public void aplicarModalidaQuincenal(int diaQuincenaInicial, int diaQuincenFinal, double SemanasDePlazo, double valorCuota) {
        // Datos de modalida quincenal
        eModalida = "Quincenal";
        gDiaQuincenaInicial = diaQuincenaInicial;
        hDiaQuincenaFinal = diaQuincenFinal;
        jNumeroCuotas = SemanasDePlazo;
        kValorCuotas = valorCuota;
    }

    @Override
    public void aplicarModalidaMensual(int diasMesCobrar, double mesDePlazo, double valorCuota) {
        //Datos de modalidad mensual
        eModalida = "Mensual";
        iDiaMensual = diasMesCobrar;
        jNumeroCuotas = mesDePlazo;
        kValorCuotas = valorCuota;
    }
}
