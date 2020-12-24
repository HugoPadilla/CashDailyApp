package com.wenitech.cashdaily.Data.repository;

import androidx.lifecycle.MutableLiveData;

import com.wenitech.cashdaily.Data.model.UserApp;
import com.wenitech.cashdaily.Util.StartedLogin;
import com.wenitech.cashdaily.Util.StartedSingIn;
import com.wenitech.cashdaily.Data.dataBase.LoginDataBaseModel;

public class LoginRepository {

    LoginDataBaseModel loginDataBaseModel;

    MutableLiveData<StartedLogin> startedLoginMutableLiveData;
    MutableLiveData<StartedSingIn> startedSignInMutableLiveData;

    //Todo: constructor
    public LoginRepository() {
        loginDataBaseModel = new LoginDataBaseModel();
        startedLoginMutableLiveData = loginDataBaseModel.get_statedLogin();
        startedSignInMutableLiveData = loginDataBaseModel.get_startedSingIn();
    }

    // Todo: Getters and Setters
    public MutableLiveData<StartedLogin> getStartedLoginMutableLiveData() {
        if (startedLoginMutableLiveData == null) {
            startedLoginMutableLiveData = new MutableLiveData<>();
        }
        return startedLoginMutableLiveData;
    }

    public MutableLiveData<StartedSingIn> getStartedSignInMutableLiveData() {
        if (startedSignInMutableLiveData == null) {
            startedSignInMutableLiveData = new MutableLiveData<>();
        }
        return startedSignInMutableLiveData;
    }

    // Todo: Metodo Public
    // Login repository to Data base
    public void loginRepository(String email, String password) {
        loginDataBaseModel.LoginDataBase(email, password);
    }

    // Sing In repository to Data Base
    public void signInRepository(String email, String password, UserApp user) {
        loginDataBaseModel.SignInDataBase(email, password, user);
    }

}
