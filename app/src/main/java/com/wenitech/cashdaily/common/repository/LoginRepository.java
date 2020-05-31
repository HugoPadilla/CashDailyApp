package com.wenitech.cashdaily.common.repository;

import androidx.lifecycle.MutableLiveData;

import com.wenitech.cashdaily.common.dataBase.LoginDataBaseModel;

public class LoginRepository {

    LoginDataBaseModel loginDataBaseModel;
    private MutableLiveData<Boolean> mensaje;
    private MutableLiveData<Boolean> IsLoginProcess;

    public LoginRepository() {
        loginDataBaseModel = new LoginDataBaseModel();
        mensaje = loginDataBaseModel.getIsLoginSucess();
        IsLoginProcess = loginDataBaseModel.getIsLoginProcces();
    }

    public void IniciarSesion(String email, String password) {
        loginDataBaseModel.InciarSesion(email,password);
    }

    public MutableLiveData<Boolean> getMensaje() {
        return mensaje;
    }

    public MutableLiveData<Boolean> IsLoginProccess() {
        return IsLoginProcess;
    }
}
