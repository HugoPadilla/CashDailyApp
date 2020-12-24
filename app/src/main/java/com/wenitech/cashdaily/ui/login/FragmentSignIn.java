package com.wenitech.cashdaily.ui.login;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.Util.StartedSingIn;
import com.wenitech.cashdaily.databinding.FragmentSignInBinding;
import com.wenitech.cashdaily.viewModel.login.LoginViewModel;

import org.jetbrains.annotations.NotNull;

public class FragmentSignIn extends Fragment {

    private FragmentSignInBinding binding;
    private LoginViewModel viewModel;
    private NavController navController;

    public FragmentSignIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = Navigation.findNavController(binding.getRoot());

        setupOnClickListener();
        setupObserver();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                viewModel.onCancelSingIn();
                navController.navigate(R.id.action_fragmentSignIn_to_fragmentLogin);
            }
        });
    }

    private void setupOnClickListener() {
    }

    private void setupObserver() {

        viewModel.get_EmailMessageError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textInputLayoutCrearCuentaCorreo.setError(s);
            }
        });

        viewModel.get_PasswordMessageError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textInputLayoutSignInPassword.setError(s);
            }
        });

        viewModel.get_PasswordConfirmMessageError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textInputLayoutSignInConfirmPassword.setError(s);
            }
        });

        viewModel.get_StartedSingIn().observe(getViewLifecycleOwner(), new Observer<StartedSingIn>() {
            @Override
            public void onChanged(StartedSingIn startedSingIn) {
                switch (startedSingIn) {
                    case SING_IN_INIT:
                        onProgress(false);
                        break;
                    case SING_IN_PROCESS:
                        onProgress(true);
                        break;
                    case SING_IN_SUCCESS:
                        updateUi();
                        break;
                    case SING_IN_COLLISION:
                        DialogInfoCollision();
                        onProgress(false);
                        break;
                    case SING_IN_FAILED:
                        DialogInfoFailed();
                        onProgress(false);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + startedSingIn);
                }
            }
        });
    }

    private void DialogInfoCollision() {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Cuenta existente")
                .setMessage("Parece que ya existes una cuenta con este correo, intenta iniciar sesion o recupera tu contraseña")
                .setPositiveButton("Iniciar sesion", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        navController.navigate(R.id.action_fragmentSignIn_to_fragmentLogin);
                        viewModel.onCancelSingIn();
                    }
                })
                .setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    private void DialogInfoFailed() {
        Toast.makeText(getContext(), "Open Dialogo Failed", Toast.LENGTH_SHORT).show();
    }


    private void onProgress(boolean isProgress) {
        if (isProgress) {
            binding.groupView.setVisibility(View.GONE);
            binding.groupProgress.setVisibility(View.VISIBLE);
        } else {
            binding.groupView.setVisibility(View.VISIBLE);
            binding.groupProgress.setVisibility(View.GONE);
        }
    }

    private void updateUi() {
        navController.navigate(R.id.action_fragmentSignIn_to_mainNavActivity);
        requireActivity().finish();
    }
}