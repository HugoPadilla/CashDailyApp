package com.wenitech.cashdaily.Data.dataBase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.wenitech.cashdaily.Data.model.UserApp;
import com.wenitech.cashdaily.Util.StartedResetPassword;
import com.wenitech.cashdaily.Util.StartedSingIn;
import com.wenitech.cashdaily.common.ApiAccessSingleton.FirebaseAuthAPI;
import com.wenitech.cashdaily.Util.StartedLogin;

public class LoginDataBaseModel {

    private FirebaseAuthAPI mAuth;

    private MutableLiveData<StartedLogin> _statedLogin = new MutableLiveData<>();
    private MutableLiveData<StartedSingIn> _startedSingIn = new MutableLiveData<>(StartedSingIn.SING_IN_INIT);
    private MutableLiveData<StartedResetPassword> _startedResetPassword = new MutableLiveData<>();

    // Constructor para iniciar las intancia de la FirebaseAuth
    public LoginDataBaseModel() {
        mAuth = FirebaseAuthAPI.getInstance();
        get_statedLogin();
    }

    // Getter Setter
    // Todo: Refactorizar: debe devolver un livedata en ves de MutableLivedata
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

    public MutableLiveData<StartedResetPassword> get_startedResetPassword() {
        if (_startedResetPassword == null){
            _startedResetPassword = new MutableLiveData<>();
        }
        return _startedResetPassword;
    }

    /**
     * Metodo recursivo para devolver el mismo parametro que recibe
     * para poder insertar su valor en una MutableLiveData de tipo StartedLogin
     *
     * @param stateLoginIs
     * @return
     */
    private StartedLogin DefStateLogin(int stateLoginIs) {
        StartedLogin startedLogin = new StartedLogin();
        startedLogin.setStateLogin(stateLoginIs);
        return startedLogin;
    }

    private StartedResetPassword DefStartedResetPassword(int statedResetPassword){
        StartedResetPassword startedResetPassword = new StartedResetPassword();
        startedResetPassword.setStatesResetPassword(statedResetPassword);
        return startedResetPassword;
    }

    /**
     * Login in dadaBase
     *
     * @param email
     * @param password
     */
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

    /**
     * New account for user with Email
     *
     * @param email
     * @param password
     * @param user
     */
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

    /**
     * Reset account for new password.
     * Send  the email at user for recovery account
     *
     * @param email
     * @param password
     */
    public void ResetPassword(String email, String password) {
        get_startedResetPassword().setValue(DefStartedResetPassword(StartedResetPassword.RESET_PASSWORD_PROCESS));

        mAuth.getAuthInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            get_startedResetPassword().setValue(DefStartedResetPassword(StartedResetPassword.RESET_PASSWORD_SUCCESS));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                get_startedResetPassword().setValue(DefStartedResetPassword(StartedResetPassword.RESET_PASSWORD_FAILED));
            }
        });
    }
}
