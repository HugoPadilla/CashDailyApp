package com.wenitech.cashdaily.common.dataBase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.wenitech.cashdaily.common.ApiAccessSingleton.FirebaseAuthAPI;

public class LoginDataBaseModel {

    private FirebaseAuthAPI firebaseAuthAPI;
    private MutableLiveData<Boolean> isLoginSucess;
    private MutableLiveData<Boolean> IsLoginProcess;

    public LoginDataBaseModel() {
        firebaseAuthAPI = FirebaseAuthAPI.getInstance();
        getIsLoginSucess();
        getIsLoginProcces();
    }

    public MutableLiveData<Boolean> getIsLoginProcces() {
        if (IsLoginProcess == null){
            IsLoginProcess = new MutableLiveData<>();
        }
        return IsLoginProcess;
    }

    public MutableLiveData<Boolean> getIsLoginSucess() {
        if (isLoginSucess == null){
            isLoginSucess = new MutableLiveData<>();
        }
        return isLoginSucess;
    }

    public void InciarSesion(final String emai, String password) {
        getIsLoginProcces().setValue(true);
        firebaseAuthAPI.getAuthIntance().signInWithEmailAndPassword(emai,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    getIsLoginSucess().setValue(true);
                    getIsLoginProcces().setValue(false);
                }else {
                  getIsLoginSucess().setValue(false);
                  getIsLoginProcces().setValue(false);
                }
            }
        });
    }

}
