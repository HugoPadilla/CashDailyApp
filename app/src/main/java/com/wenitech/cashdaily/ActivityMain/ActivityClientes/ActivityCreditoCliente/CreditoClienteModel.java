package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityCreditoCliente;

public class CreditoClienteModel implements CreditoClienteInterface.model {
    private CreditoClienteInterface.taskListener taskListener;

    public CreditoClienteModel(CreditoClienteInterface.taskListener taskListener) {
        this.taskListener = taskListener;
    }
}
