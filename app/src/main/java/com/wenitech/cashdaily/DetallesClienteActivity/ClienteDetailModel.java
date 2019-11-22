package com.wenitech.cashdaily.DetallesClienteActivity;

public class ClienteDetailModel implements ClienteDetailInterface.model {
    private ClienteDetailInterface.taskListener taskListener;

    public ClienteDetailModel(ClienteDetailInterface.taskListener taskListener) {
        this.taskListener = taskListener;
    }
}
