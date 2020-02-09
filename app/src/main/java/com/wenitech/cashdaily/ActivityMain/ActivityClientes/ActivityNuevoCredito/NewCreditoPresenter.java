package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityNuevoCredito;

import com.wenitech.cashdaily.Model.Credito;

public class NewCreditoPresenter implements NewCreditoInterface.presente, NewCreditoInterface.taskListener {
    private NewCreditoModel model;
    private NewCreditActivity view;

    public NewCreditoPresenter(NewCreditActivity view) {
        this.view = view;
        model = new NewCreditoModel(this);
    }

    @Override
    public void onSucces() {
        view.mostrarView();
        view.onSucces();
    }

    @Override
    public void onError() {
        view.mostrarView();
        view.onError();
    }

    @Override
    public void registrarCredito(Credito credito,String referenciaCliente) {
        view.ocultarView();
        model.registrarCredito(credito,referenciaCliente);
    }
}
