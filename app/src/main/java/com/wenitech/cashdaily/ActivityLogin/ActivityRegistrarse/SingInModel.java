package com.wenitech.cashdaily.ActivityLogin.ActivityRegistrarse;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wenitech.cashdaily.common.pojo.Usuairio;

import java.util.Objects;

public class SingInModel implements InterfaceSingIn.model {

    private InterfaceSingIn.taskListener taskListener;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;
    private Usuairio usuairio;

    public SingInModel(InterfaceSingIn.taskListener taskListener) {
        this.taskListener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        db =FirebaseFirestore.getInstance();
    }

    @Override
    public void CrearCuenta(final String userName, final String tipoCuenta, final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
                    mUser = mAuth.getCurrentUser();
                    mUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                            }
                        }
                    });

                    String inicialNombre = userName.substring(0,1).toUpperCase();
                    usuairio = new Usuairio(Timestamp.now(),userName,inicialNombre,tipoCuenta,"Free");
                    db.collection("usuarios").document(Objects.requireNonNull(mAuth.getUid())).set(usuairio);
                    taskListener.onSucess(email);
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
