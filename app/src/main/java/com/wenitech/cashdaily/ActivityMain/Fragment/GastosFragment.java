package com.wenitech.cashdaily.ActivityMain.Fragment;

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
import com.wenitech.cashdaily.Adapter.RecyclerViewGastoAdapter;
import com.wenitech.cashdaily.Model.Gasto;
import com.wenitech.cashdaily.R;

public class GastosFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewGastoAdapter mAdapter;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference =  db
            .collection("usuarios").document(mAuth.getUid())
            .collection("gastos");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gastos,container,false);

        Query query = collectionReference.orderBy("fecha",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Gasto> options = new FirestoreRecyclerOptions.Builder<Gasto>()
                .setQuery(query,Gasto.class).build();

        recyclerView = view.findViewById(R.id.recyclerViewGastos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerViewGastoAdapter(options);
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
