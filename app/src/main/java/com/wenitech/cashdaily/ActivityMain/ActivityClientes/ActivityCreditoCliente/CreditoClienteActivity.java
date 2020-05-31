package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityCreditoCliente;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.wenitech.cashdaily.Adapter.RecyclerViewCuotaAdapter;
import com.wenitech.cashdaily.common.pojo.Credito;
import com.wenitech.cashdaily.common.pojo.Cuota;
import com.wenitech.cashdaily.PerfilClienteActivity;
import com.wenitech.cashdaily.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;


public class CreditoClienteActivity extends AppCompatActivity implements View.OnClickListener, CreditoClienteInterface.view {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser mUser;

    private CardView cardViewCredito;
    private CollectionReference colRefCuotas;
    private DocumentReference documentReferenceCreditoActivo;
    private TextView
            textViewMoimientos,
            textViewPorcentaje,
            textViewDetallesPrestamo,
            textViewModalidad,
            textViewPrestamoTotal,
            textViewNumeroCuotas,
            textViewValorCuota,
            textViewDeudaCredito;
    private View divideMovimientos;
    private FloatingActionButton fabButtomNuevaCuota;

    private RecyclerViewCuotaAdapter recyclerViewCuotaAdapter;
    private RecyclerView recyclerViewCuotas;

    private ProgressDialog progressDialog;

    private CreditoClienteInterface.presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credito_cliente);
        presenter = new CreditoClientePresenter(this);

        instanciarFirebase();
        iniciarToolbar();
        iniciarAppBar();
        castingView();
        iniciarRecyclerView();
        listenerView();
        snapshotCredito();
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

    @Override
    public void instanciarFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void iniciarToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar_credito_cliente);
        mToolbar.setSubtitle(getIntent().getStringExtra("CLIENTE_INTENT_NOMBRE"));
        mToolbar.setNavigationIcon(R.drawable.ic_system_flecha_atras_blanco);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void iniciarAppBar() {
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);
    }

    @Override
    public void castingView() {
        recyclerViewCuotas = findViewById(R.id.recycler_view_credito_cliente_lista_cuotas);
        cardViewCredito = findViewById(R.id.card_view_credito_cliente_credito);

        textViewMoimientos = findViewById(R.id.text_view_credito_cliente_movimientos);
        divideMovimientos = findViewById(R.id.divider_credito_cliente_movimientos);
        textViewDetallesPrestamo = findViewById(R.id.text_view_credito_cliente_ver_detalles_prestamo);

        textViewModalidad = findViewById(R.id.text_view_credito_cliente_modalidad);
        textViewPorcentaje = findViewById(R.id.text_view_credito_cliente_porcentaje);
        textViewPrestamoTotal = findViewById(R.id.text_view_credito_cliente_prestamo_total);
        textViewNumeroCuotas = findViewById(R.id.text_view_credito_cliente_numero_cuotas);
        textViewValorCuota = findViewById(R.id.text_view_credito_cliente_valor_cuota);
        textViewDeudaCredito = findViewById(R.id.text_view_credito_cliente_deuta_credito);
        fabButtomNuevaCuota = findViewById(R.id.fab_button_credito_cliente_nueva_cuota);
    }

    @Override
    public void iniciarRecyclerView() {
        colRefCuotas = db.document(getIntent().getStringExtra("REFERENCIA_CREDITO_ACTIVO"))
                .collection("cuotas");

        Query query = colRefCuotas.orderBy("fechaCreacion", Query.Direction.DESCENDING).limit(4);

        FirestoreRecyclerOptions<Cuota> options = new FirestoreRecyclerOptions.Builder<Cuota>()
                .setQuery(query, Cuota.class).build();

        recyclerViewCuotaAdapter = new RecyclerViewCuotaAdapter(options);
        recyclerViewCuotas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCuotas.setAdapter(recyclerViewCuotaAdapter);
    }

    @Override
    public void listenerView() {
        fabButtomNuevaCuota.setOnClickListener(this);
        textViewDetallesPrestamo.setOnClickListener(this);
    }

    @Override
    public void snapshotCredito() {
        String referenciaCreditoActivo = getIntent().getStringExtra("REFERENCIA_CREDITO_ACTIVO");
        documentReferenceCreditoActivo = db.document(referenciaCreditoActivo);

        documentReferenceCreditoActivo.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(CreditoClienteActivity.this, "error en documento snapshop", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Credito credito = documentSnapshot.toObject(Credito.class);

                    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
                    simbolo.setDecimalSeparator(',');
                    simbolo.setGroupingSeparator('.');
                    DecimalFormat decimalFormat = new DecimalFormat("###,###.##", simbolo);

                    textViewModalidad.setText("Modalidad: " + credito.getModalida());

                    String porcentaje = decimalFormat.format(credito.getPorcentaje());
                    textViewPorcentaje.setText("- " + porcentaje + "%");

                    String prestamoTotal = decimalFormat.format(credito.getTotalPrestamo());
                    textViewPrestamoTotal.setText("$" + prestamoTotal);

                    String numeroCuotas = decimalFormat.format(credito.getNumeroCuotas());
                    textViewNumeroCuotas.setText(numeroCuotas);

                    String valorCuota = decimalFormat.format(credito.getValorCuota());
                    textViewValorCuota.setText("$" + valorCuota);

                    String deudaCredito = decimalFormat.format(credito.getDeudaPrestamo());
                    textViewDeudaCredito.setText("$" + deudaCredito);
                }
            }
        });
    }

    @Override
    public void dialogoNuevaCuota() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreditoClienteActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialogo_nueva_cuota, null);
        final TextInputEditText editTextNuevaCuota = view.findViewById(R.id.edit_text_dialogo_nueva_cuota);

        builder.setView(view);
        builder.setTitle("Agregar nueva cuota");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevaCuota = editTextNuevaCuota.getText().toString().trim();
                if (nuevaCuota.isEmpty()) {
                    dialogoOnError("Se debes agrega un valor para realizar la transaccion");
                } else {
                    Cuota cuota = new Cuota(Timestamp.now(), mUser.getDisplayName(), "Normal", Double.parseDouble(nuevaCuota));
                    presenter.agregarNuevaCuota(cuota,getIntent().getStringExtra("REFERENCIA_CREDITO_ACTIVO"), getIntent().getStringExtra("REFERENCIA_CLIENTE"));
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CreditoClienteActivity.this, "Cancelar", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void abriDialogoProgreso() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Agregando Cuota");
        progressDialog.show();
    }

    @Override
    public void cerraDialogoProgreso() {
        progressDialog.dismiss();
    }

    @Override
    public void dialogoOnSucces(String mensaje) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Succes")
                .setMessage(mensaje)
                .setPositiveButton("Entendido", null)
                .show();
    }

    @Override
    public void dialogoOnError(String mensaje) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Tenemos un problema")
                .setMessage(mensaje)
                .setPositiveButton("Entendido", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_botton_app_client_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_bottom_app_bar_perfil:
                startActivity(new Intent(CreditoClienteActivity.this, PerfilClienteActivity.class));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_button_credito_cliente_nueva_cuota:
                dialogoNuevaCuota();
                break;
            case R.id.text_view_credito_cliente_ver_detalles_prestamo:
                break;
        }
    }
}
