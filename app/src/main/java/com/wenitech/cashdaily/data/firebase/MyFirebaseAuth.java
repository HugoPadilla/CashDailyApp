package com.wenitech.cashdaily.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyFirebaseAuth {

    private static MyFirebaseAuth INSTANCE = null;
    private static FirebaseAuth mAuthInstance;
    private static FirebaseUser mUserInstance;

    private MyFirebaseAuth(){
        mAuthInstance = FirebaseAuth.getInstance();
        mUserInstance = mAuthInstance.getCurrentUser();
    }

    public static MyFirebaseAuth getInstance(){
        if (INSTANCE == null){
            INSTANCE = new MyFirebaseAuth();
        }
        return INSTANCE;
    }

    public FirebaseAuth getAuth(){
        return mAuthInstance;
    }

    public FirebaseUser getUser(){
        return mUserInstance;
    }

}
