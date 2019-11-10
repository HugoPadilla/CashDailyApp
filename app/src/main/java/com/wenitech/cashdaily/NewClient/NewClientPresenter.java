package com.wenitech.cashdaily.NewClient;

import com.google.firebase.Timestamp;

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
    public void presentarRegistro(String identificacion, String nombre, String inicialNombre, String telefono, String ubicacion, int valorPrestamo, int deudaPrestamo, boolean estado) {
        view.hidenform();
        view.showPrgres();
        model.registrar(identificacion,nombre,inicialNombre,telefono,ubicacion,0,0,true);
    }

    @Override
    public void succes() {
        if (view != null) {
            view.hidenProgres();
            view.showform();
            view.onSuces();
        }
    }

    @Override
    public void error() {
        if (view !=null) {
            view.hidenProgres();
            view.showform();
            view.onError();
        }

    }
}
