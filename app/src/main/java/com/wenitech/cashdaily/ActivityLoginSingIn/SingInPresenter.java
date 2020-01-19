package com.wenitech.cashdaily.ActivityLoginSingIn;

public class SingInPresenter implements InterfaceSingIn.presenter, InterfaceSingIn.taskListener {

    InterfaceSingIn.view view;
    InterfaceSingIn.model model;

    public SingInPresenter(InterfaceSingIn.view view) {
        this.view = view;
        model = new SingInModel(this);
    }

    @Override
    public void CrearCuenta(String userName, String email, String password) {
        if (view !=null){
            view.HidenFormulario();
            model.CrearCuenta(userName,email,password);
        }
    }

    @Override
    public void onSucess(String emailSucess) {
        if (view != null){
            view.onSucess(emailSucess);
        }
    }

    @Override
    public void onExist() {
        if (view != null){
            view.ShowFormulario();
            view.onExist();
        }
    }

    @Override
    public void onError() {
        if (view != null){
            view.ShowFormulario();
            view.onError();
        }
    }
}
