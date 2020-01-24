package com.wenitech.cashdaily.ActivityClientes.Fragment;

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

public class TodosFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewClienteAdapter mAdapter;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference =  db
            .collection("usuarios").document(mAuth.getUid())
            .collection("clientes");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todos,container,false);

        Query query = collectionReference.orderBy("nombreCliente",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Cliente> options = new FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query,Cliente.class).build();

        recyclerView = view.findViewById(R.id.recycler_view_todos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerViewClienteAdapter(options);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
