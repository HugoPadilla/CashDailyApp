package com.wenitech.cashdaily.NewGastoActivity;

public class NewGastoModel implements InterfaceNewGasto.model {

    InterfaceNewGasto.taskListener taskListener;

    public NewGastoModel(InterfaceNewGasto.taskListener taskListener) {
        this.taskListener = taskListener;
    }
}
