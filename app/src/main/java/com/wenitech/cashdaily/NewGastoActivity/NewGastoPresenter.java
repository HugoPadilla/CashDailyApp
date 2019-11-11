package com.wenitech.cashdaily.NewGastoActivity;

import com.wenitech.cashdaily.GastoDetail.GastoDetailModel;

public class NewGastoPresenter implements InterfaceNewGasto.presenter, InterfaceNewGasto.taskListener {

    private InterfaceNewGasto.view view;
    private InterfaceNewGasto.model model;

    public NewGastoPresenter(InterfaceNewGasto.view view) {
        this.view = view;
        model = new NewGastoModel(this);
    }

    @Override
    public void GuardarDatos(String valor, String fecha, String hora, String descripcion) {
        if (view!=null){
            view.ShowPrograsBar();
            model.GuardarDatos(valor,fecha,hora,descripcion);
        }
    }

    @Override
    public void Sucess() {
        if (view != null){
            view.HidenProgresBar();
            view.OnSucess();
        }
    }

    @Override
    public void Error() {
        if (view != null){
            view.HidenProgresBar();
            view.OnError();
        }
    }
}
