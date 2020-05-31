package com.wenitech.cashdaily.viewModel;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wenitech.cashdaily.common.repository.LoginRepository;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> EmailMutable;
    public MutableLiveData<String> PasswordMutable;

    public MutableLiveData<String> TextInputLayoutEmailError;
    public MutableLiveData<String> TextInputLayoutPasswordError;
    public MutableLiveData<Boolean> IsLoginSuccess;
    public MutableLiveData<Boolean> LoginProcess;

    private LoginRepository loginRepository;

    // COnstructor
    public LoginViewModel() {
        loginRepository = new LoginRepository();
        IsLoginSuccess = loginRepository.getMensaje();
        LoginProcess = loginRepository.IsLoginProccess();
    }

    public MutableLiveData<String> getEmail() {
        if (EmailMutable == null) {
            EmailMutable = new MutableLiveData<>();
        }return EmailMutable;
    }

    public MutableLiveData<String> getPasswordMutable() {
        if (PasswordMutable == null){
            PasswordMutable = new MutableLiveData<>();
        }return PasswordMutable;
    }

    public MutableLiveData<String> getTextInputLayoutEmailError() {
        if (TextInputLayoutEmailError == null) {
            TextInputLayoutEmailError = new MutableLiveData<>();
        }return TextInputLayoutEmailError;
    }

    public MutableLiveData<String> getTextInputLayoutPasswordError() {
        if (TextInputLayoutPasswordError == null){
            TextInputLayoutPasswordError = new MutableLiveData<>();
        }return TextInputLayoutPasswordError;
    }

    public MutableLiveData<Boolean> getIsLoginSuccess() {
        if (IsLoginSuccess == null) {
            IsLoginSuccess = new MutableLiveData<>();
        }return IsLoginSuccess;
    }

    public MutableLiveData<Boolean> isLoginProcess() {
        if (LoginProcess == null){
            LoginProcess = new MutableLiveData<>();
        }return LoginProcess;
    }

    public void iniciarSesion() {
        if (isEmailValido(EmailMutable.getValue()) && isPasswrdValido(PasswordMutable.getValue())) {
            loginRepository.IniciarSesion(EmailMutable.getValue(), PasswordMutable.getValue());
        } else {

        }
    }

    private Boolean isEmailValido(String email) {

        boolean valido = true;
        if (TextUtils.isEmpty(email)) {
            TextInputLayoutEmailError.setValue("Escribe un correo");
            valido = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            TextInputLayoutEmailError.setValue("No es un correo valido");
            valido = false;
        } else {
            getTextInputLayoutEmailError().setValue("");
        }return valido;
    }

    private Boolean isPasswrdValido(String password) {
        boolean valid = true;
        if (TextUtils.isEmpty(password)){
            TextInputLayoutPasswordError.setValue("Escribe una contraseña");
            valid = false;
        } else if (password.length() < 8){
            TextInputLayoutPasswordError.setValue("Minimo 8 carapteres");
            valid = false;
        } else {
            TextInputLayoutPasswordError.setValue("");
        } return valid;
    }

}
