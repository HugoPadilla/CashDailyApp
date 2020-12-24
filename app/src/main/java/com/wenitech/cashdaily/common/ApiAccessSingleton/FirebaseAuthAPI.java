package com.wenitech.cashdaily.common.ApiAccessSingleton;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthAPI {

    private static FirebaseAuthAPI INSTANCE = null;
    private static FirebaseAuth mAuthInstance;

    private  FirebaseAuthAPI(){
        mAuthInstance = FirebaseAuth.getInstance();
    }

    public static FirebaseAuthAPI getInstance(){
        if (INSTANCE == null){
            INSTANCE = new FirebaseAuthAPI();
        }
        return INSTANCE;
    }

    public FirebaseAuth getAuthInstance(){
        return mAuthInstance;
    }

}
