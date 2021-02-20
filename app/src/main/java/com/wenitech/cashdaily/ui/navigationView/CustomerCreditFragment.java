package com.wenitech.cashdaily.ui.navigationView;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.wenitech.cashdaily.Data.model.Credito;
import com.wenitech.cashdaily.Data.remoteDatabase.MyFirebaseAuth;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.databinding.FragmentCustomerCreditBinding;
import com.wenitech.cashdaily.viewModel.CustomerCreditViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;

public class CustomerCreditFragment extends Fragment {

    private FragmentCustomerCreditBinding binding;
    private CustomerCreditViewModel todoViewModel;

    private String idClient;
    private String referenceCredit;

    public CustomerCreditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerCreditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCreditActive();
    }

    private Boolean initCreditActive() {
        CustomerCreditFragmentArgs args = CustomerCreditFragmentArgs.fromBundle(getArguments());
        idClient = args.getReferenceClient();
        if (args.getIsCreditActive()) {
            // Todo: Si tiene un credito activo recibir la refenecia del cliente y del credito mediante args
            referenceCredit = args.getReferenceCredit();

            binding.groupContentCustomer.setVisibility(View.VISIBLE);
            binding.groupCustomerVacio.setVisibility(View.GONE);
            return true;
        } else {
            binding.groupContentCustomer.setVisibility(View.GONE);
            binding.groupCustomerVacio.setVisibility(View.VISIBLE);
            return false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTodoViewModel();
        initViewOnClickListener();
        initObservable();
    }

    private void initTodoViewModel() {
        todoViewModel = new ViewModelProvider(requireActivity()).get(CustomerCreditViewModel.class);
    }

    private void initViewOnClickListener() {
        // Click open dialog from new quota
        binding.fabButtonCustomerCreditNewQuota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogNewQuota();
            }
        });

        // Click go to activity new credit
        binding.buttonNewCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a NewCireditFramgnet y pasar el IdCliente
                CustomerCreditFragmentDirections.ActionCustomerCreditFragmentToNewCreditFragment action = CustomerCreditFragmentDirections.actionCustomerCreditFragmentToNewCreditFragment(idClient);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    private void initObservable() {
        if (initCreditActive()) {
            todoViewModel.getCreditClient(referenceCredit);
            todoViewModel.creditLiveData.observe(getViewLifecycleOwner(), new Observer<Credito>() {
                @Override
                public void onChanged(Credito credito) {
                    Toast.makeText(getContext(), String.valueOf(credito.getTotalPrestamo()), Toast.LENGTH_SHORT).show();
                    NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
                    binding.textViewCreditDisponible.setText(numberFormat.format(credito.getDeudaPrestamo()));
                }
            });
        }
    }

    /**
     * Open dialog for newQuota
     */
    private void openDialogNewQuota() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getLayoutInflater().inflate(R.layout.dialogo_nueva_cuota, null);
        final TextInputEditText editTextNuevaCuota = view.findViewById(R.id.edit_text_dialogo_nueva_cuota);

        builder.setView(view);
        builder.setTitle("Agregar nueva cuota");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String nuevaCuota = editTextNuevaCuota.getText().toString().trim();

                if (nuevaCuota.isEmpty()) {
                    // On error form

                } else {

                    Double newQuota = Double.parseDouble(editTextNuevaCuota.getText().toString().trim());
                    todoViewModel.addNewQuota(newQuota, idClient, referenceCredit, MyFirebaseAuth.getInstance().getUser());

                }

            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}