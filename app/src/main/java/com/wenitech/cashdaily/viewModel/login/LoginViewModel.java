 package com.wenitech.cashdaily.viewModel.login;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wenitech.cashdaily.Data.model.UserApp;
import com.wenitech.cashdaily.Data.repository.LoginRepository;
import com.wenitech.cashdaily.Util.StartedLogin;
import com.wenitech.cashdaily.Util.StartedSingIn;

public class LoginViewModel extends ViewModel {

    // Edit text
    private MutableLiveData<String> _Email;
    private MutableLiveData<String> _Password;
    private MutableLiveData<String> _PasswordConfirm;

    // Messenger Edit Text
    private MutableLiveData<String> _EmailMessageError;
    private MutableLiveData<String> _PasswordMessageError;
    private MutableLiveData<String> _PasswordConfirmMessageError;

    // state del Login y Sing in
    private MutableLiveData<StartedLogin> _StartedLogin;
    private MutableLiveData<StartedSingIn> _StartedSingIn;

    private LoginRepository loginRepository;

    // Todo: Constructor
    public LoginViewModel() {

        this.loginRepository = new LoginRepository();

        _StartedLogin = loginRepository.getStartedLoginMutableLiveData();
        _StartedSingIn = loginRepository.getStartedSignInMutableLiveData();
    }

    //Todo: Getter Setter
    public MutableLiveData<String> get_Email() {
        if (_Email == null) {
            _Email = new MutableLiveData<String>("");
        }
        return _Email;
    }

    public MutableLiveData<String> get_EmailMessageError() {
        if (_EmailMessageError == null) {
            _EmailMessageError = new MutableLiveData<>();
        }
        return _EmailMessageError;
    }

    public MutableLiveData<String> get_Password() {
        if (_Password == null) {
            _Password = new MutableLiveData<String>();
        }
        return _Password;
    }

    public MutableLiveData<String> get_PasswordMessageError() {
        if (_PasswordMessageError == null) {
            _PasswordMessageError = new MutableLiveData<>();
        }
        return _PasswordMessageError;
    }

    public MutableLiveData<String> get_PasswordConfirm() {
        if (_PasswordConfirm == null) {
            _PasswordConfirm = new MutableLiveData<String>();
        }
        return _PasswordConfirm;
    }

    public MutableLiveData<String> get_PasswordConfirmMessageError() {
        if (_PasswordConfirmMessageError ==null){
            _PasswordConfirmMessageError = new MutableLiveData<String>();
        }
        return _PasswordConfirmMessageError;
    }

    public MutableLiveData<StartedLogin> get_StartedLogin() {
        if (_StartedLogin == null) {
            _StartedLogin = new MutableLiveData<>();
        }
        return _StartedLogin;
    }

    public MutableLiveData<StartedSingIn> get_StartedSingIn() {
        if (_StartedSingIn == null) {
            _StartedSingIn = new MutableLiveData<>();
        }
        return _StartedSingIn;
    }


    //Todo: Metodos Publicos
    public void logIn() {

        String email = get_Email().getValue();
        String password = get_Password().getValue();

        if (isValidEmail(email != null ? email.trim() : "") && isValidPassword(password != null ? password.trim() : "")) {
            loginRepository.loginRepository(_Email.getValue().trim(), _Password.getValue().trim());
        }
    }

    public void signIn() {

        String email = _Email.getValue();
        String password = _Password.getValue();
        String passwordConfirm = _PasswordConfirm.getValue();

        if (isValidEmail(email != null ? email.trim() : "") && isValidPassword(password != null ? password.trim() : "")
                && isValidPasswordConfirm(password != null ? password.trim() : "", passwordConfirm != null ? passwordConfirm.trim() : "")) {
            loginRepository.signInRepository(email, password, new UserApp(false, "MANAGER", "FREE"));
        }
    }

    public boolean onCancelSingIn(){

        StartedLogin startedLogin = new StartedLogin();
        startedLogin.setStateLogin(StartedLogin.LOGIN_IN_INIT);

        _StartedLogin.setValue(startedLogin);
        _StartedSingIn.setValue(StartedSingIn.SING_IN_INIT);
        _EmailMessageError.setValue("");
        _Password.setValue("");
        _PasswordMessageError.setValue("");
        get_PasswordConfirm().setValue("");
        get_PasswordConfirmMessageError().setValue("");
        return true;
    }

    //Todo: Metodos Pirvados
    private Boolean isValidEmail(String email) {
        boolean valid = true;
        if (TextUtils.isEmpty(email)) {
            _EmailMessageError.setValue("Escribe un correo");
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _EmailMessageError.setValue("No es un correo valido");
            valid = false;
        } else {
            get_EmailMessageError().setValue("");
        }
        return valid;
    }

    private Boolean isValidPassword(String password) {
        boolean valid = true;
        if (TextUtils.isEmpty(password)) {
            _PasswordMessageError.setValue("Escribe una contraseña");
            valid = false;
        } else if (password.length() < 8) {
            _PasswordMessageError.setValue("Debe tener minimo 8 carapteres");
            valid = false;
        } else {
            _PasswordMessageError.setValue("");
        }
        return valid;
    }

    private boolean isValidPasswordConfirm(String password, String passwordConfirm) {
        boolean valid = true;
        if (TextUtils.isEmpty(passwordConfirm)){
            _PasswordConfirmMessageError.setValue("Debes confirmar tu contraseña");
            valid = false;
        }else if (!TextUtils.equals(password, passwordConfirm)) {
            _PasswordConfirmMessageError.setValue("Contraseñas no coinciden");
            _PasswordConfirm.setValue("");
            valid = false;
        } else {
            _PasswordMessageError.setValue("");
            _PasswordConfirmMessageError.setValue("");
        }

        return valid;
    }
}
