package com.wenitech.cashdaily.LoginActivity;

public class LoginActivityPresenter implements InterfaceLoginActivity.presenter, InterfaceLoginActivity.taskListener {
    InterfaceLoginActivity.view view;
    InterfaceLoginActivity.model model;

    public LoginActivityPresenter(InterfaceLoginActivity.view view) {
        this.view = view;
        model = new LoginActivityModel(this);
    }


    @Override
    public void IniciarSesion(String email, String password) {
        if (view != null){
            view.HidenFormLogin();
            model.InciarSesion(email,password);
        }
    }

    @Override
    public void LoginSuces() {
        if (view != null){
            view.ShowFormLogin();
            view.updatesUi();
        }
    }

    @Override
    public void LoginError() {
        if (view != null){
            view.ShowFormLogin();
            view.OnErro();
        }
    }
}
