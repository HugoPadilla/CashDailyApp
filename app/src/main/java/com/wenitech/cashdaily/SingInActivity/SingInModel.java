package com.wenitech.cashdaily.SingInActivity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wenitech.cashdaily.Model.Usuairio;

public class SingInModel implements InterfaceSingIn.model {
    private InterfaceSingIn.taskListener taskListener;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Usuairio usuairio;


    public SingInModel(InterfaceSingIn.taskListener taskListener) {
        this.taskListener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        db =FirebaseFirestore.getInstance();
    }

    @Override
    public void CrearCuenta(final String userName, final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    usuairio = new Usuairio(userName);
                    db.collection("usuarios").document(mAuth.getUid()).set(usuairio);
                    taskListener.onSucess();
                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        taskListener.onExist();
                    }else {
                        taskListener.onError();
                    }
                }
            }
        });
    }
}
