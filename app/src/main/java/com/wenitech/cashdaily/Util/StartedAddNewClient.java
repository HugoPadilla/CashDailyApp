package com.wenitech.cashdaily.Util;

import androidx.annotation.IntDef;

import org.checkerframework.checker.nullness.compatqual.PolyNullType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StartedAddNewClient {

    public static final int STATED_ADD_NEW_CLIENT_INIT = 0;
    public static final int STATED_ADD_NEW_CLIENT_PROCESS = 1;
    public static final int STATED_ADD_NEW_CLIENT_SUCCESS = 2;
    public static final int STATED_ADD_NEW_CLIENT_FAILED = 3;
    public static final int STATED_ADD_NEW_CLIENT_CANCEL = 4;

    @IntDef({STATED_ADD_NEW_CLIENT_INIT, STATED_ADD_NEW_CLIENT_PROCESS, STATED_ADD_NEW_CLIENT_SUCCESS, STATED_ADD_NEW_CLIENT_FAILED, STATED_ADD_NEW_CLIENT_CANCEL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface statedAssNewClient{

    }

    public int statedAssNewClient;

    @statedAssNewClient
    public int getStatedAddNewClient(){
        return statedAssNewClient;
    }

    public void setStatedAddNewClient(@statedAssNewClient int statedAssNewClient){
        this.statedAssNewClient = statedAssNewClient;
    }
}
