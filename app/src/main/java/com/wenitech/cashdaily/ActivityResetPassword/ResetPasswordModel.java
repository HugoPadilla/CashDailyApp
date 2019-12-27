package com.wenitech.cashdaily.ActivityResetPassword;

public class ResetPasswordModel implements InterfaceResetPasswordActivity.Model{
    InterfaceResetPasswordActivity.TaskListener taskListener;

    public ResetPasswordModel(InterfaceResetPasswordActivity.TaskListener taskListener) {
        this.taskListener = taskListener;
    }
}
