package com.wenitech.cashdaily.MainActivity.Fragment;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.wenitech.cashdaily.Adapter.RecyclerViewClienteAdapter;
import com.wenitech.cashdaily.Model.Cliente;
import com.wenitech.cashdaily.R;

public class ClientesFragment extends Fragment {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ClientesRef = db.collection("usuarios").document(mAuth.getUid())
            .collection("clientes");

    private RecyclerViewClienteAdapter mRecyclerviewClienteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clientes,container,false);

        Query query = ClientesRef.orderBy("dNombreCliente", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Cliente> options = new FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query,Cliente.class).build();

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerviewClienteAdapter = new RecyclerViewClienteAdapter(options);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRecyclerviewClienteAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerviewClienteAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRecyclerviewClienteAdapter.stopListening();
    }
}
