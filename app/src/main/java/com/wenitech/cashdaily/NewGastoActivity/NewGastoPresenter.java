package com.wenitech.cashdaily.NewGastoActivity;

public class NewGastoPresenter implements InterfaceNewGasto.presenter, InterfaceNewGasto.taskListener {

    private InterfaceNewGasto.view view;
    private InterfaceNewGasto.model model;

    public NewGastoPresenter(InterfaceNewGasto.view view) {
        this.view = view;
        model = new NewGastoModel(this);
    }
}
