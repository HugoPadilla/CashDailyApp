package com.wenitech.cashdaily.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wenitech.cashdaily.CambiarPasswordActivity;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.ActivityLogin.ActivityRegistrarse.SingInActivity;
import com.wenitech.cashdaily.viewModel.LoginViewModel;
import com.wenitech.cashdaily.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    //Objeto view model
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configurarBindinAndViewModel();
        configurarObserver();
        bindingListener();
    }

    private void configurarBindinAndViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLoginViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void configurarObserver() {
        viewModel.getIsLoginSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    updatesUi();
                }
            }
        });

        viewModel.getTextInputLayoutEmailError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textInputLayoutEmail.setError(s);
            }
        });

        viewModel.getTextInputLayoutPasswordError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textInputLayoutPassword.setError(s);
            }
        });
        viewModel.isLoginProcess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    binding.imageViewLogo.setVisibility(View.GONE);
                    binding.textViewIniciarSesionRegistrarse.setVisibility(View.GONE);
                    binding.textviewResetPassword.setVisibility(View.GONE);
                    binding.textInputLayoutEmail.setVisibility(View.GONE);
                    binding.textInputLayoutPassword.setVisibility(View.GONE);
                    binding.buttonIniciarSesion.setVisibility(View.GONE);
                    binding.textView4.setVisibility(View.GONE);
                    binding.textViewIngresando.setVisibility(View.VISIBLE);
                    binding.progressBarLogin.setVisibility(View.VISIBLE);
                }else{
                    binding.imageViewLogo.setVisibility(View.VISIBLE);
                    binding.textViewIniciarSesionRegistrarse.setVisibility(View.VISIBLE);
                    binding.textviewResetPassword.setVisibility(View.VISIBLE);
                    binding.textInputLayoutEmail.setVisibility(View.VISIBLE);
                    binding.textInputLayoutPassword.setVisibility(View.VISIBLE);
                    binding.buttonIniciarSesion.setVisibility(View.VISIBLE);
                    binding.textView4.setVisibility(View.VISIBLE);
                    binding.textViewIngresando.setVisibility(View.GONE);
                    binding.progressBarLogin.setVisibility(View.GONE);
                }
            }
        });
    }

    private void bindingListener() {

        // Activity registrarse
        binding.textViewIniciarSesionRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SingInActivity.class);
                startActivity(intent);
            }
        });

        binding.textviewResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CambiarPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updatesUi() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
