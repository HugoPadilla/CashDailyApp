package com.wenitech.cashdaily.ActivityMain.ActivityCaja;

public class CajaPresenter implements CajaActivityInterface.presenter, CajaActivityInterface.taskListener {

    private CajaActivityInterface.view view;
    private CajaActivityInterface.model model;

    public CajaPresenter(CajaActivityInterface.view view) {
        this.view = view;
        model = new CajaModel(this);
    }

    @Override
    public void OnDestroid() {

    }

    @Override
    public void agregarDineroCaja(double valorAgregar) {
        view.abrirDialogoProgreso("Agregando dinero...");
        model.agregarDineroCaja(valorAgregar);
    }

    @Override
    public void retirarDineroCaja(double valorRetirar) {
        view.abrirDialogoProgreso("Retirando dinero...");
        model.retirarDineroCaja(valorRetirar);
    }

    @Override
    public void OnSucess(String mensaje) {
        view.cerraDialogoProgreso();
        view.mensajeOnSucces(mensaje);
    }

    @Override
    public void OnError(String mensaje) {
        view.cerraDialogoProgreso();
        view.mensajeOnError(mensaje);
    }
}
