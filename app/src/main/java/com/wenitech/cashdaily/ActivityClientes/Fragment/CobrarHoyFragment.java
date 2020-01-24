package com.wenitech.cashdaily.ActivityClientes.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.wenitech.cashdaily.Adapter.RecyclerViewClienteAdapter;
import com.wenitech.cashdaily.Model.Cliente;
import com.wenitech.cashdaily.R;

import java.util.Date;

public class CobrarHoyFragment extends Fragment {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference documentoListaCliente = db.collection("usuarios").document(auth.getUid()).collection("clientes");
    RecyclerViewClienteAdapter recyclerViewClienteAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cobrar_hoy, container, false);

        Date fechahoy = new Date();

        String fecha = String.valueOf(fechahoy.getTime());

        Query query = documentoListaCliente.orderBy("nombreCliente",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Cliente> options = new FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query,Cliente.class).build();

        RecyclerView recyclerViewCobrarHoy = view.findViewById(R.id.recycler_view_cobrar_hoy);
        recyclerViewClienteAdapter = new RecyclerViewClienteAdapter(options);
        recyclerViewCobrarHoy.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCobrarHoy.setAdapter(recyclerViewClienteAdapter);

        return view;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {

                    Usuairio usuairio = documentSnapshot.toObject(Usuairio.class);
                    if (usuairio != null) {

                        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
                        simbolo.setDecimalSeparator(',');
                        simbolo.setGroupingSeparator('.');
                        DecimalFormat decimalFormat = new DecimalFormat("###,###.##", simbolo);

                        //tv_efectivo.setText(decimalFormat.format(usuairio.getcTotalEfectivo()));
                        //tv_hoy.setText(decimalFormat.format(usuairio.getdCobradoHoy()));
                        //tv_este_mes.setText(decimalFormat.format(usuairio.geteCobradoMes()));
                    }
                } else {

                }

            }
        });*/

    }


}
