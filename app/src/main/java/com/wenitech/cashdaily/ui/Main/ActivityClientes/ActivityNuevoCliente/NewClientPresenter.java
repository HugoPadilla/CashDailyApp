package com.wenitech.cashdaily.ui.Main.ActivityClientes.ActivityNuevoCliente;

public class NewClientPresenter implements Interface.presenter, Interface.taskListener {

    private Interface.view view;
    private Interface.model model;

    public NewClientPresenter(Interface.view view) {
        this.view = view;
        model = new NewClientModel(this);
    }

    @Override
    public void onDestroid() {
        view = null;
    }

    @Override
    public void presentarRegistro(String nombreCliente, String identificacion, String genero, String telefono, String ciudad,String direccion) {
        view.hidenForm();
        view.showProgres();
        model.registrar(nombreCliente,identificacion,genero,telefono,ciudad,direccion);
    }

    @Override
    public void succes() {
        if (view != null) {
            view.hidenProgres();
            view.showForm();
            view.onSucces();
        }
    }

    @Override
    public void error() {
        if (view !=null) {
            view.hidenProgres();
            view.showForm();
            view.onError();
        }

    }
}
