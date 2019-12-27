package com.wenitech.cashdaily.HisotrialPrestamosActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.wenitech.cashdaily.Adapter.RecyclerViewCreditosAdapter;
import com.wenitech.cashdaily.Model.Credito;
import com.wenitech.cashdaily.R;

public class HistorialPrestamosActivity extends AppCompatActivity {

    private String REFERENICIA_CLIENTE;
    private DocumentReference documentReferenceCliente;
    private CollectionReference collectionReferenceHistorialPrestamos;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_prestamos);

        REFERENICIA_CLIENTE = getIntent().getStringExtra("id_cliente_ref");

        collectionReferenceHistorialPrestamos = db.document(REFERENICIA_CLIENTE).collection("/historial");

        ConfigurarRecyclerViewHistorialPrestamos();
    }

    private void ConfigurarRecyclerViewHistorialPrestamos() {

        Query queryHistorialFechaPrestamo = collectionReferenceHistorialPrestamos.orderBy("aFechaCreacion", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Credito> creditoFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Credito>().setQuery(queryHistorialFechaPrestamo,Credito.class).build();

        RecyclerView mRecyclerViewHistorialPrestamos = findViewById(R.id.recyclerview_historial_prestamos);
        RecyclerViewCreditosAdapter recyclerViewCreditosAdapter = new RecyclerViewCreditosAdapter(creditoFirestoreRecyclerOptions);
        mRecyclerViewHistorialPrestamos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerViewHistorialPrestamos.setAdapter(recyclerViewCreditosAdapter);


    }
}
