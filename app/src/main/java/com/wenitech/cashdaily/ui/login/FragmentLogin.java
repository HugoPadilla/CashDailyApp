package com.wenitech.cashdaily.ui.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.Util.StartedLogin;
import com.wenitech.cashdaily.databinding.FragmentLoginBinding;
import com.wenitech.cashdaily.viewModel.login.LoginViewModel;

import org.jetbrains.annotations.NotNull;

public class FragmentLogin extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    public FragmentLogin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setLoginViewModel(loginViewModel);
        binding.setLifecycleOwner(this);

        setupOnClickListener();
        setupObserverViewModel();
    }

    //Todo: observer of view model var
    private void setupObserverViewModel() {

        loginViewModel.get_EmailMessageError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textInputLayoutEmail.setError(s);
            }
        });

        loginViewModel.get_PasswordMessageError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textInputLayoutPassword.setError(s);
            }
        });

        loginViewModel.get_StartedLogin().observe(getViewLifecycleOwner(), new Observer<StartedLogin>() {
            @Override
            public void onChanged(StartedLogin startedLogin) {
                switch (startedLogin.getStateLogin()) {
                    case StartedLogin.LOGIN_IN_INIT:
                        onProgress(false);
                        break;
                    case StartedLogin.LOGIN_IN_PROCESS:
                        onProgress(true);
                        break;
                    case StartedLogin.LOGIN_SUCCESS:
                        updateUi();
                        break;
                    case StartedLogin.LOGIN_FAILED:
                        onProgress(false);
                        Toast.makeText(getContext(), "Correo o contraseña invalidos", Toast.LENGTH_SHORT).show();
                        break;
                    case StartedLogin.LOGIN_CANCEL:

                        break;
                }
            }
        });
    }

    private void onProgress(boolean isProgress) {

        if (isProgress) {
            binding.groupView.setVisibility(View.GONE);
            binding.groupProgressBar.setVisibility(View.VISIBLE);
        } else {
            binding.groupView.setVisibility(View.VISIBLE);
            binding.groupProgressBar.setVisibility(View.GONE);
        }

    }

    private void setupOnClickListener() {
        binding.buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.logIn();
            }
        });

        binding.textviewResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.action_fragmentLogin_to_fragmentRecoverPassword);
            }
        });

        binding.textViewIniciarSesionRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.action_fragmentLogin_to_fragmentSignIn);
                loginViewModel.onCancelSingIn();
            }
        });

        binding.editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.isFocused() == hasFocus) {
                    Toast.makeText(getContext(), "Focused", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUi() {
        Navigation.findNavController(requireView()).navigate(R.id.action_fragmentLogin_to_mainNavActivity);
        requireActivity().finish();
    }
}