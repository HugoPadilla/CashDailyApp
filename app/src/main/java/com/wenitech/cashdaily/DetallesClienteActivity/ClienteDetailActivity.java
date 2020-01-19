package com.wenitech.cashdaily.DetallesClienteActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;
import com.wenitech.cashdaily.Adapter.RecyclerViewCuotaAdapter;
import com.wenitech.cashdaily.Model.Cliente;
import com.wenitech.cashdaily.Model.Cuota;
import com.wenitech.cashdaily.NewCreditoActivity.NewCreditActivity;
import com.wenitech.cashdaily.R;

import java.util.Objects;

import static java.lang.Integer.parseInt;

public class ClienteDetailActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionRefCuotas;
    DocumentReference documentRefCliente;
    private RecyclerViewCuotaAdapter recyclerViewCuotaAdapter;
    private String ID_CLIENTE_REFRENCIA;
    private Toolbar toolbar;
    private Dialog mDialogo;

    private Cliente cliente;

    private TextView tv_identficacion_cliente, tv_ubicacion_cliente, tv_prestamo_actual, tv_deuda_prestamo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detail);

        ClienteDetailInterface.presenter presenter = new ClienteDetailPresenter(this);
        /*ID_CLIENTE_REFRENCIA = getIntent().getStringExtra("id_cliente_ref");*/

        mDialogo = new Dialog(this);

        /*addToolbar();
        addVies();*/
        /*addRecyclerview();
        addViewsListener();
        addSnapListener();*/
    }

    private void addViewsListener() {
    }

    /*private void addSnapListener() {
        documentRefCliente = db.document(ID_CLIENTE_REFRENCIA);
        documentRefCliente.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    cliente = documentSnapshot.toObject(Cliente.class);
                    if (cliente != null) {
                        tv_identficacion_cliente.setText(cliente.getcIdentificacion());
                        tv_ubicacion_cliente.setText(cliente.getgUbicacion());
                        tv_prestamo_actual.setText(String.valueOf(cliente.gethValorPrestamo()));
                        tv_deuda_prestamo.setText(String.valueOf(cliente.getiDeudaPrestamo()));
                    }
                } else {
                    //Document not Exist
                }
            }
        });
    }*/

    /*private void addToolbar() {
        toolbar = findViewById(R.id.toolbar_cliente_detail);
        toolbar.setTitle(getIntent().getStringExtra("id_cliente_name"));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }*/

    /*private void addVies() {
        tv_identficacion_cliente = findViewById(R.id.tv_identificacion_cliente);
        tv_ubicacion_cliente = findViewById(R.id.tv_ubicacion_cliente);
        tv_prestamo_actual = findViewById(R.id.tv_prestamo_actual);
        tv_deuda_prestamo = findViewById(R.id.tv_deuda_pretamo);
    }*/

    private void addRecyclerview() {
        assert ID_CLIENTE_REFRENCIA != null;
        collectionRefCuotas = db.document(ID_CLIENTE_REFRENCIA)
                .collection("/creditos").document("credito").collection("cuotas");

        //Quety que para ordenar por fecha
        Query query = collectionRefCuotas.orderBy("bFechaCreacion", Query.Direction.DESCENDING);

        //Firebase option que se enviara a el adaptadro
        FirestoreRecyclerOptions<Cuota> options = new FirestoreRecyclerOptions.Builder<Cuota>()
                .setQuery(query, Cuota.class).build();

        /*RecyclerView recyclerViewCuotas = findViewById(R.id.recyclerviewCredito);
        recyclerViewCuotaAdapter = new RecyclerViewCuotaAdapter(options);
        recyclerViewCuotas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCuotas.setNestedScrollingEnabled(true);
        recyclerViewCuotas.setAdapter(recyclerViewCuotaAdapter);*/
    }

    @Override
    public void onStart() {
        super.onStart();
        //recyclerViewCuotaAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //recyclerViewCuotaAdapter.stopListening();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_botton_app_client_detail, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuItemNewCredit) {
            Intent intent = new Intent(ClienteDetailActivity.this, NewCreditActivity.class);
            intent.putExtra("ID_CLIENTE_REFERENCIA",ID_CLIENTE_REFRENCIA);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
*/
   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }*/

    /*@Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_agregar_cuota) {
            ShowDialogo();
        }
    }*/

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

                    final DocumentReference documentRefCredito = db.document(ID_CLIENTE_REFRENCIA)
                            .collection("/creditos").document("credito");

                    documentRefCredito.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            //Credito credito = documentSnapshot.toObject(Credito.class);
                            final String valorCuota = edt_valor_cuota.getText().toString().trim();

                            if (!documentSnapshot.exists()) {
                                edt_valor_cuota.setError("Primero agrega un prestamo");

                            } else if (cliente.getiDeudaPrestamo() == 0) {
                                edt_valor_cuota.setError("El prestamo ya ha finalizado");

                            } else if (parseInt(valorCuota) > cliente.getiDeudaPrestamo()) {
                                edt_valor_cuota.setError("Cuota mayor a la deuda");

                            } else {
                                writheBart(valorCuota);
                                mDialogo.dismiss();
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

    private void writheBart(String valorCuota) {
        WriteBatch batch = db.batch();

        //objet cuota by timestap and count
        Cuota cuota = new Cuota(Timestamp.now(), Integer.parseInt(valorCuota));

        // add new cuot
        batch.set(collectionRefCuotas.document(), cuota);
        // add decrement deuda
        batch.update(documentRefCliente, "iDeudaPrestamo", FieldValue.increment(-(parseInt(valorCuota))));

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mDialogo.dismiss();
                } else if (task.getException() != null) {
                    Toast.makeText(ClienteDetailActivity.this, "Escritura en lote no realizada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
