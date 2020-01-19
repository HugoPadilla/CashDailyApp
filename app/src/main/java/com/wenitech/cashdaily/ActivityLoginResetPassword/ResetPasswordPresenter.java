package com.wenitech.cashdaily.ActivityLoginResetPassword;

public class ResetPasswordPresenter implements InterfaceResetPasswordActivity.Presenter, InterfaceResetPasswordActivity.TaskListener{
    private InterfaceResetPasswordActivity.View mView;
    private InterfaceResetPasswordActivity.Model mModel;

    public ResetPasswordPresenter(InterfaceResetPasswordActivity.View mView) {
        this.mView = mView;
        mModel = new ResetPasswordModel(this);
    }
}
