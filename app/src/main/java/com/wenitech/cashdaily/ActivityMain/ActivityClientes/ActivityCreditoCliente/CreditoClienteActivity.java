package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityCreditoCliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.wenitech.cashdaily.Adapter.RecyclerViewCuotaAdapter;
import com.wenitech.cashdaily.Model.Cuota;
import com.wenitech.cashdaily.R;

import java.util.Objects;


public class CreditoClienteActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser mUser;

    private DocumentReference docRefCliente;
    private CollectionReference colRefCuotas;


    private TextView
            textViewDetallesPrestamo,
            textViewModalidad,
            textViewPrestamoTotal,
            textViewNumeroCuotas,
            textViewValorCuota,
            textViewDeudaCredito;

    private RecyclerViewCuotaAdapter recyclerViewCuotaAdapter;
    private RecyclerView recyclerViewCuotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credito_cliente);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();
        configurarToolbar();
        configurarBottomAppBar();
        configurarCastingViewClickListener();
        configurarRecyclerview();
    }

    private void configurarCastingViewClickListener() {
        textViewDetallesPrestamo = findViewById(R.id.text_view_credito_cliente_ver_detalles_prestamo);
        textViewModalidad = findViewById(R.id.text_view_credito_cliente_modalidad);
        textViewPrestamoTotal = findViewById(R.id.text_view_credito_cliente_prestamo_total);
        textViewNumeroCuotas= findViewById(R.id.text_view_credito_cliente_numero_cuotas);
        textViewValorCuota= findViewById(R.id.text_view_credito_cliente_valor_cuota);
        textViewDeudaCredito = findViewById(R.id.text_view_credito_cliente_deuta_credito);
        FloatingActionButton fabButtomNuevaCuota = findViewById(R.id.fab_button_credito_cliente_nueva_cuota);
        fabButtomNuevaCuota.setOnClickListener(this);
        textViewDetallesPrestamo.setOnClickListener(this);
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
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }

    private void configurarToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar_credito_cliente);
        mToolbar.setNavigationIcon(R.drawable.ic_system_flecha_atras_blanco);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void configurarBottomAppBar(){
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_botton_app_client_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_bottom_app_bar_perfil:
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_bottom_app_bar_telefono:
                Toast.makeText(this, "Telefono", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_bottom_app_bar_historial:
                Toast.makeText(this, "Hostorial", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_bottom_app_bar_eliminar:
                Toast.makeText(this, "eliminar", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configurarRecyclerview() {
        String ref_cliente = getIntent().getStringExtra("REFERENCIA_CLIENTE");
        colRefCuotas = db.document(ref_cliente).collection("cuotas");

        Query query = colRefCuotas.orderBy("fechaCreacion", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Cuota> options = new FirestoreRecyclerOptions.Builder<Cuota>()
                .setQuery(query, Cuota.class).build();

        RecyclerView recyclerViewCuotas = findViewById(R.id.recycler_view_credito_cliente_lista_cuotas);
        recyclerViewCuotaAdapter = new RecyclerViewCuotaAdapter(options);
        recyclerViewCuotas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCuotas.setAdapter(recyclerViewCuotaAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_button_credito_cliente_nueva_cuota:
                Toast.makeText(this, "nueva cuota", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_view_credito_cliente_ver_detalles_prestamo:
                break;
        }
    }
}
