package com.wenitech.cashdaily.Util;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StartedResetPassword {
    public static final int RESET_PASSWORD_INIT = 0;
    public static final int RESET_PASSWORD_PROCESS = 1;
    public static final int RESET_PASSWORD_SUCCESS = 2;
    public static final int RESET_PASSWORD_FAILED = 3;
    public static final int RESET_PASSWORD_CANCEL = 4;

    @IntDef({RESET_PASSWORD_INIT, RESET_PASSWORD_PROCESS, RESET_PASSWORD_SUCCESS, RESET_PASSWORD_FAILED, RESET_PASSWORD_CANCEL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface statesResetPassword {

    }

    public int statesResetPassword;

    @statesResetPassword
    public int getResetPasswordInit() {
        return statesResetPassword;
    }

    public void setStatesResetPassword(@statesResetPassword int statesResetPassword) {
        this.statesResetPassword = statesResetPassword;
    }
}
