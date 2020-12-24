package com.wenitech.cashdaily.ui.Main.ActivityClientes.ActivityCreditoCliente;

import com.wenitech.cashdaily.Data.model.Cuota;

public class CreditoClientePresenter implements CreditoClienteInterface.presenter, CreditoClienteInterface.taskListener{

    private CreditoClienteActivity view;
    private CreditoClienteModel model;

    public CreditoClientePresenter(CreditoClienteActivity view) {
        this.view = view;
        model = new CreditoClienteModel(this);
    }

    @Override
    public void agregarNuevaCuota(Cuota cuota, String referenciaCredito, String referenciasCliente) {
        view.abriDialogoProgreso();
        model.agregarNuevaCuota(cuota, referenciaCredito, referenciasCliente);
    }

    @Override
    public void onSucces(String mensaje) {
        view.cerraDialogoProgreso();
        view.dialogoOnSucces(mensaje);
    }

    @Override
    public void onError(String mensaje) {
        view.cerraDialogoProgreso();
        view.dialogoOnError(mensaje);
    }
}
