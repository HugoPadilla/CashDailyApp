package com.wenitech.cashdaily.Data.dataBase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.wenitech.cashdaily.Data.model.UserApp;
import com.wenitech.cashdaily.Util.StartedSingIn;
import com.wenitech.cashdaily.common.ApiAccessSingleton.FirebaseAuthAPI;
import com.wenitech.cashdaily.Util.StartedLogin;

public class LoginDataBaseModel {

    private FirebaseAuthAPI mAuth;

    private MutableLiveData<StartedLogin> _statedLogin = new MutableLiveData<>();
    private MutableLiveData<StartedSingIn> _startedSingIn = new MutableLiveData<>(StartedSingIn.SING_IN_INIT);

    // Todo: Constructor
    public LoginDataBaseModel() {
        mAuth = FirebaseAuthAPI.getInstance();
        get_statedLogin();
    }

    //Todo:Getter Setter
    public MutableLiveData<StartedLogin> get_statedLogin() {
        if (_statedLogin == null) {
            _statedLogin = new MutableLiveData<>();
        }
        return _statedLogin;
    }

    public MutableLiveData<StartedSingIn> get_startedSingIn() {
        if (_startedSingIn == null) {
            _startedSingIn = new MutableLiveData<>();
        }
        return _startedSingIn;
    }

    // Metod of return stateLogin
    private StartedLogin DefStateLogin(int stateLoginIs) {

        StartedLogin startedLogin = new StartedLogin();
        startedLogin.setStateLogin(stateLoginIs);

        return startedLogin;
    }

    //Todo: Login DataBase
    // Login Database
    public void LoginDataBase(final String email, String password) {

        get_statedLogin().setValue(DefStateLogin(StartedLogin.LOGIN_IN_PROCESS));

        mAuth.getAuthInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    get_statedLogin().setValue(DefStateLogin(StartedLogin.LOGIN_SUCCESS));
                } else {
                    get_statedLogin().setValue(DefStateLogin(StartedLogin.LOGIN_FAILED));
                }

            }
        });
    }

    // SingIn Database
    public void SignInDataBase(String email, String password, UserApp user) {
        get_startedSingIn().setValue(StartedSingIn.SING_IN_PROCESS);
        mAuth.getAuthInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

            }
        }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    get_startedSingIn().setValue(StartedSingIn.SING_IN_SUCCESS);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        get_startedSingIn().setValue(StartedSingIn.SING_IN_COLLISION);
                    } else {
                        get_startedSingIn().setValue(StartedSingIn.SING_IN_FAILED);
                    }
                }
            }
        });
    }

    // Reset Password Database
    public void ResetPassword(String email, String password) {

    }
}
