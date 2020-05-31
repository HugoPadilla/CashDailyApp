package com.wenitech.cashdaily.common.ApiAccessSingleton;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthAPI {

    private FirebaseAuth mAuthIntance;
    private static FirebaseAuthAPI INSTANCE = null;

    private FirebaseAuthAPI(){
        mAuthIntance = FirebaseAuth.getInstance();
    }

    public static FirebaseAuthAPI getInstance(){
        if (INSTANCE == null){
            INSTANCE = new FirebaseAuthAPI();
        }
        return INSTANCE;
    }

    public FirebaseAuth getAuthIntance(){
        return mAuthIntance;
    }

}
