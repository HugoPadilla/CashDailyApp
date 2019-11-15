package com.wenitech.cashdaily.ClienteDetail.FragmentNavigation;

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
import com.wenitech.cashdaily.Adapter.AdapterRecyclerCredito;
import com.wenitech.cashdaily.Model.Credito;
import com.wenitech.cashdaily.R;

public class CreditoNavFragment extends Fragment {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String idClienteref;

    private AdapterRecyclerCredito adapterRecyclerCredito;
    private RecyclerView recyclerViewCredito;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_nav_credito,container,false);

        idClienteref = getArguments().getString("id_cliente_ref_credito");
        CollectionReference collectionReference = db.document(idClienteref).collection("/creditos");

        Query query = collectionReference.orderBy("fechaCuota",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Credito> options = new FirestoreRecyclerOptions.Builder<Credito>()
                .setQuery(query,Credito.class).build();

        recyclerViewCredito = view.findViewById(R.id.recyclerviewCredito);
        adapterRecyclerCredito = new AdapterRecyclerCredito(options);
        recyclerViewCredito.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCredito.setAdapter(adapterRecyclerCredito);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterRecyclerCredito.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterRecyclerCredito.stopListening();
    }
}
