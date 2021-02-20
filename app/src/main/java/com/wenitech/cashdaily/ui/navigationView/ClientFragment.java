package com.wenitech.cashdaily.ui.navigationView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.wenitech.cashdaily.NavGraphMainDirections;
import com.wenitech.cashdaily.databinding.FragmentClientBinding;
import com.wenitech.cashdaily.ui.Adapter.RecyclerViewClienteAdapter;
import com.wenitech.cashdaily.Data.model.Cliente;
import com.wenitech.cashdaily.R;

public class ClientFragment extends Fragment implements RecyclerViewClienteAdapter.ReciclerViewClienteInteface {

    private FragmentClientBinding binding;
    private NavController navController;
    private RecyclerViewClienteAdapter mAdapter;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference =  db
            .collection("usuarios").document(mAuth.getUid())
            .collection("clientes");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClientBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireView());
        setupRecyclerView();
        setupClickListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void setupRecyclerView() {
        Query query = collectionReference.orderBy("fullName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Cliente> options = new FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query,Cliente.class).build();

        binding.recyclerViewTodos.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerViewClienteAdapter(options,this);
        binding.recyclerViewTodos.setAdapter(mAdapter);
    }

    private void setupClickListener() {
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_clientFragment_to_newClientActivity);
            }
        });
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

    @Override
    public void onClientClicked(View cardView, Cliente cliente) {
        NavGraphMainDirections.ActionGlobalCustomerCreditFragment action = NavGraphMainDirections.actionGlobalCustomerCreditFragment(cliente.getId(),cliente.getDocumentReferenceCreditActive().getPath());
        action.setIsCreditActive(cliente.isCreditActive());
        navController.navigate(action);
    }
}
