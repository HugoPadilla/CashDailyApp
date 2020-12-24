package com.wenitech.cashdaily.Util;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StartedLogin {
    // Nota: Mejorar los comentatrios

    // Denifnimos nuestro opciones como interos y su nombre personalizado
    public static final int LOGIN_IN_INIT    = 0;
    public static final int LOGIN_IN_PROCESS = 1;
    public static final int LOGIN_SUCCESS    = 2;
    public static final int LOGIN_FAILED     = 3;
    public static final int LOGIN_CANCEL     = 4;

    // Esta interfas no permite poder definir los nombres personalizados del un enum
    @IntDef({LOGIN_IN_INIT, LOGIN_IN_PROCESS, LOGIN_SUCCESS, LOGIN_FAILED, LOGIN_CANCEL })
    @Retention(RetentionPolicy.SOURCE)
    public @interface stateLogin{
        // Interface custom
    }

    // Getter y Setter para del enumerador
    public int stateLogin;

    @stateLogin
    public int getStateLogin(){
        return stateLogin;
    }

    public void setStateLogin(@stateLogin int stateLogin){
        this.stateLogin = stateLogin;
    }
}

