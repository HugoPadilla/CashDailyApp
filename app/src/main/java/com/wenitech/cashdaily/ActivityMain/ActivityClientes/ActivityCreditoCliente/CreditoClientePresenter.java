package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityCreditoCliente;

public class CreditoClientePresenter implements CreditoClienteInterface.presenter, CreditoClienteInterface.taskListener{

    private CreditoClienteActivity view;
    private CreditoClienteModel model;

    public CreditoClientePresenter(CreditoClienteActivity view) {
        this.view = view;
        model = new CreditoClienteModel(this);
    }
}
