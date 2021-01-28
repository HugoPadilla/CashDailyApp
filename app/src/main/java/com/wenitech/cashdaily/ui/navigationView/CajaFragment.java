package com.wenitech.cashdaily.ui.navigationView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.wenitech.cashdaily.Data.model.Caja;
import com.wenitech.cashdaily.Data.model.MovimientoCaja;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.databinding.FragmentCajaBinding;
import com.wenitech.cashdaily.ui.Adapter.RecyclerViewGastoAdapter;
import com.wenitech.cashdaily.viewModel.MainViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;

public class CajaFragment extends Fragment {

    private FragmentCajaBinding binding;
    private MainViewModel viewModel = new MainViewModel();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerViewGastoAdapter recyclerViewGastoAdapter;
    private CollectionReference collectionReferenceMovimientos = db
            .collection("usuarios").document(auth.getUid())
            .collection("caja").document("myCaja")
            .collection("movimientos");

    public CajaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCajaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        observerViewModel();
        onClickViewLinestener();
        setupRecyclerViewMovimientos();
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerViewGastoAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerViewGastoAdapter.stopListening();
    }

    private void setupRecyclerViewMovimientos() {
        Query query = collectionReferenceMovimientos.orderBy("fecha", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<MovimientoCaja> options = new FirestoreRecyclerOptions.Builder<MovimientoCaja>()
                .setQuery(query, MovimientoCaja.class).build();

        recyclerViewGastoAdapter = new RecyclerViewGastoAdapter(options);
        binding.recyclerViewCajaTransaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewCajaTransaciones.setAdapter(recyclerViewGastoAdapter);
    }

    private void onClickViewLinestener() {
        binding.cardViewCajaAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoAgregarDinero();
            }
        });

        binding.cardViewCajaRetirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoRetirarDinero();
            }
        });
    }

    private void observerViewModel() {
        viewModel.getCaja().observe(getViewLifecycleOwner(), new Observer<Caja>() {
            @Override
            public void onChanged(Caja caja) {
                NumberFormat formatMoney = NumberFormat.getCurrencyInstance();
                binding.textViewCajaTotalDisponible.setText(formatMoney.format(caja.getTotalCaja()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void dialogoAgregarDinero() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialogo_agregar_dinero_caja, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        final TextInputLayout textInputLayoutAgregar = view.findViewById(R.id.text_input_layout_dialogo_agregar_dinero_caja_valo);
        final TextInputEditText editTextValor = view.findViewById(R.id.edit_text_dialogo_agregar_dinero_caja_valor);
        final AppCompatButton buttonAgregar = view.findViewById(R.id.button_dialogo_agregar_dinero_caja_agregar);
        final ImageView imageViewCerrarDialogo = view.findViewById(R.id.image_view_dialogo_agregar_dinero_cerrar);

        imageViewCerrarDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valor = editTextValor.getText().toString().trim();
                if (valor.isEmpty()) {
                    textInputLayoutAgregar.setError("Escribe un valor");
                } else {
                    buttonAgregar.setEnabled(false);
                    final double valorAgregar = Double.parseDouble(valor);
                    dialog.dismiss();
                    viewModel.addMoneyOnBox(valorAgregar);
                }
            }
        });
        dialog.show();
    }

    public void dialogoRetirarDinero() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialogo_retirar_dinero_caja, null);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        final TextInputLayout textInputLayoutRetirar = view.findViewById(R.id.text_input_layout_dialogo_retirar_dinero_caja_val);
        final TextInputEditText editTextValor = view.findViewById(R.id.edit_text_dialogo_agregar_dinero_caja_valor);
        final AppCompatButton buttonAgregar = view.findViewById(R.id.button_dialogo_agregar_dinero_caja_agregar);
        final ImageView imageViewCerrarDialogo = view.findViewById(R.id.image_view_dialogo_retirar_dinero_cerrar);

        imageViewCerrarDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valor = editTextValor.getText().toString().trim();
                if (valor.isEmpty()) {
                    textInputLayoutRetirar.setError("Escribe un valor");
                } else {
                    buttonAgregar.setEnabled(false);
                    final double valorRetirar = Double.parseDouble(valor);
                    dialog.dismiss();
                    viewModel.removeMoney(valorRetirar);
                }
            }
        });
        dialog.show();
    }
}