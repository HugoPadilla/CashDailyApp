package com.wenitech.cashdaily.ActivityLoginResetPassword;

public class ResetPasswordModel implements InterfaceResetPasswordActivity.Model{
    InterfaceResetPasswordActivity.TaskListener taskListener;

    public ResetPasswordModel(InterfaceResetPasswordActivity.TaskListener taskListener) {
        this.taskListener = taskListener;
    }
}
