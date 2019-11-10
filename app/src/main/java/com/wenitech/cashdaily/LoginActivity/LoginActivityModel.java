package com.wenitech.cashdaily.LoginActivity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivityModel implements InterfaceLoginActivity.model{
    InterfaceLoginActivity.taskListener taskListener;

    FirebaseAuth mAuth;

    public LoginActivityModel(InterfaceLoginActivity.taskListener taskListener) {
        this.taskListener = taskListener;

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void InciarSesion(final String emai, String password) {
        mAuth.signInWithEmailAndPassword(emai,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    taskListener.LoginSuces();
                }else {
                    taskListener.LoginError();
                }
            }
        });
    }

}
