package com.wenitech.cashdaily.DetallesClienteActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.wenitech.cashdaily.Adapter.RecyclerViewCuotaAdapter;
import com.wenitech.cashdaily.Model.Cuota;
import com.wenitech.cashdaily.NewCreditoActivity.NewCreditActivity;
import com.wenitech.cashdaily.R;

import java.util.Date;
import java.util.Objects;

public class ClienteDetailActivity extends AppCompatActivity implements ClienteDetailInterface.view, View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference;
    private RecyclerViewCuotaAdapter recyclerViewCuotaAdapter;

    private String ID_CLIENTE_REFRENCIA;

    private Dialog mDialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detail);
        ClienteDetailInterface.presenter presenter = new ClienteDetailPresenter(this);

        mDialogo = new Dialog(this);

        Toolbar toolbar = findViewById(R.id.toolbar_cliente_detail);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Agregar Listener
        findViewById(R.id.btn_agregar_cuota).setOnClickListener(this);

        // Se obtiene el string del la referencia del cliente
        ID_CLIENTE_REFRENCIA = getIntent().getStringExtra("id_cliente_ref");

        assert ID_CLIENTE_REFRENCIA != null;
        collectionReference = db.document(ID_CLIENTE_REFRENCIA)
                .collection("/creditos").document("credito").collection("cuotas");

        //Quety que para ordenar por fecha
        Query query = collectionReference.orderBy("bFechaCreacion", Query.Direction.DESCENDING);

        //Firebase option que se enviara a el adaptadro
        FirestoreRecyclerOptions<Cuota> options = new FirestoreRecyclerOptions.Builder<Cuota>()
                .setQuery(query, Cuota.class).build();

        RecyclerView recyclerViewCuotas = findViewById(R.id.recyclerviewCredito);
        recyclerViewCuotaAdapter = new RecyclerViewCuotaAdapter(options);
        recyclerViewCuotas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCuotas.setNestedScrollingEnabled(true);
        recyclerViewCuotas.setAdapter(recyclerViewCuotaAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerViewCuotaAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerViewCuotaAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuItemNewCredit) {
            Intent intent = new Intent(ClienteDetailActivity.this, NewCreditActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_agregar_cuota) {
            ShowDialogo();
        }
    }

    private void ShowDialogo() {

        Button btn_guardar, btn_cancelar;
        final CheckBox checkBox;
        final EditText edt_valor_cuota;

        mDialogo.setContentView(R.layout.custom_dialogo_new_cuota);

        checkBox = mDialogo.findViewById(R.id.checkBox);
        btn_guardar = mDialogo.findViewById(R.id.btn_guardar_dialog);
        btn_cancelar = mDialogo.findViewById(R.id.btn_cancel_dialog);
        edt_valor_cuota = mDialogo.findViewById(R.id.edt_cuota_dialog);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valorCuota = edt_valor_cuota.getText().toString().trim();
                if (TextUtils.isEmpty(valorCuota)) {
                    edt_valor_cuota.setError("Agregué un valor");
                } else if (!checkBox.isChecked()) {
                    edt_valor_cuota.setError("¿Deasea guardar?");
                } else {

                    final DocumentReference documentReference = db.document(ID_CLIENTE_REFRENCIA)
                            .collection("/creditos").document("credito");

                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String valorCuota = edt_valor_cuota.getText().toString().trim();
                                Cuota cuota = new Cuota(Timestamp.now(), Integer.parseInt(valorCuota));
                                collectionReference.add(cuota);
                                mDialogo.dismiss();
                            }else {
                                edt_valor_cuota.setError("No existe un prestamo");
                            }
                        }

                    });

                }

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogo.dismiss();
            }
        });
        Objects.requireNonNull(mDialogo.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogo.show();
    }
}
