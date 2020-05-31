package com.wenitech.cashdaily.ActivityMain.ActivityCaja;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.wenitech.cashdaily.Adapter.RecyclerViewClienteAdapter;
import com.wenitech.cashdaily.common.pojo.Caja;
import com.wenitech.cashdaily.common.pojo.Cliente;
import com.wenitech.cashdaily.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CajaActivity extends AppCompatActivity implements View.OnClickListener, CajaActivityInterface.view {

    private CajaActivityInterface.presenter presenter;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference documentoListaCliente = db.collection("usuarios").document(mAuth.getUid()).collection("clientes");
    private RecyclerView recyclerViewCobrarHoy;
    private RecyclerViewClienteAdapter recyclerViewClienteAdapter;

    private TextView textViewCaja;
    private Button buttonAgregar, buttonRetirar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caja);
        presenter = new CajaPresenter(this);

        iniciarRecyclerViewNovimientos();
        castingViewAndListener();
        snapshotListenerCaja();

    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerViewClienteAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerViewClienteAdapter.stopListening();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_caja_agregar:
                dialogoAgregarDinero();
                break;
            case R.id.button_caja_retirar:
                dialogoRetirarDinero();
                break;
        }
    }

    @Override
    public void instanciarFirebase() {

    }

    @Override
    public void iniciarToolbar() {

    }

    @Override
    public void castingViewAndListener() {
        textViewCaja = findViewById(R.id.text_view_caja_total_caja);
        buttonAgregar = findViewById(R.id.button_caja_agregar);
        buttonRetirar = findViewById(R.id.button_caja_retirar);
        buttonAgregar.setOnClickListener(this);
        buttonRetirar.setOnClickListener(this);
    }

    @Override
    public void iniciarRecyclerViewNovimientos() {
        Query query = documentoListaCliente.orderBy("nombreCliente", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Cliente> options = new FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query, Cliente.class).build();

        recyclerViewCobrarHoy = findViewById(R.id.recycler_view_caja_transaciones);
        recyclerViewClienteAdapter = new RecyclerViewClienteAdapter(options);
        recyclerViewCobrarHoy.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCobrarHoy.setAdapter(recyclerViewClienteAdapter);
    }

    @Override
    public void snapshotListenerCaja() {
        final DocumentReference documentReferenceCaja = db.collection("usuarios").document(mAuth.getUid()).collection("caja").document("myCaja");
        documentReferenceCaja.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(CajaActivity.this, "Erro al leer total caja", Toast.LENGTH_SHORT).show();
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Caja caja = documentSnapshot.toObject(Caja.class);

                    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
                    simbolo.setDecimalSeparator(',');
                    simbolo.setGroupingSeparator('.');
                    DecimalFormat decimalFormat = new DecimalFormat("###,###.##", simbolo);

                    String totalCaja = decimalFormat.format(caja.getTotalCaja());
                    textViewCaja.setText("$" + totalCaja);
                } else {
                    Toast.makeText(CajaActivity.this, "Documento no existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void dialogoAgregarDinero() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CajaActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialogo_agregar_dinero_caja, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        final TextInputLayout textInputLayoutAgregar = view.findViewById(R.id.text_input_layout_dialogo_agregar_dinero_caja_valo);
        final TextInputEditText editTextValor = view.findViewById(R.id.edit_text_dialogo_agregar_dinero_caja_valor);
        final AppCompatButton buttonAgregar = view.findViewById(R.id.button_dialogo_agregar_dinero_caja_agregar);
        final ImageView imageViewCerrarDialogo = view.findViewById(R.id.image_view_dialogo_agregar_dinero_cerrar);

        imageViewCerrarDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valor = editTextValor.getText().toString().trim();
                if (valor.isEmpty()) {
                    textInputLayoutAgregar.setError("Escribe un valor");
                } else {
                    buttonAgregar.setEnabled(false);
                    final double valorAgregar = Double.parseDouble(valor);
                    dialog.dismiss();
                    presenter.agregarDineroCaja(valorAgregar);
                }
            }
        });
        dialog.show();
    }

    @Override
    public void dialogoRetirarDinero() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CajaActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialogo_retirar_dinero_caja, null);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        final TextInputLayout textInputLayoutRetirar = view.findViewById(R.id.text_input_layout_dialogo_retirar_dinero_caja_val);
        final TextInputEditText editTextValor = view.findViewById(R.id.edit_text_dialogo_agregar_dinero_caja_valor);
        final AppCompatButton buttonAgregar = view.findViewById(R.id.button_dialogo_agregar_dinero_caja_agregar);
        final ImageView imageViewCerrarDialogo = view.findViewById(R.id.image_view_dialogo_retirar_dinero_cerrar);

        imageViewCerrarDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valor = editTextValor.getText().toString().trim();
                if (valor.isEmpty()) {
                    textInputLayoutRetirar.setError("Escribe un valor");
                } else {
                    buttonAgregar.setEnabled(false);
                    final double valorRetirar = Double.parseDouble(valor);
                    dialog.dismiss();
                    presenter.retirarDineroCaja(valorRetirar);
                }
            }
        });
        dialog.show();
    }

    @Override
    public void abrirDialogoProgreso(String mensaje) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(mensaje);
        progressDialog.show();
    }

    @Override
    public void cerraDialogoProgreso() {
        progressDialog.dismiss();
    }

    public void mensajeOnSucces(String mensaje) {
        View contexView = findViewById(R.id.button_caja_agregar);
        Snackbar.make(contexView,mensaje,Snackbar.LENGTH_LONG).show();
    }

    public void mensajeOnError(String mensaje) {
        View contexView = findViewById(R.id.button_caja_retirar);
        Snackbar.make(contexView,mensaje,Snackbar.LENGTH_LONG).show();
    }
}
