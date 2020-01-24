package com.wenitech.cashdaily.ActivityClientes.ActivityDetallesCliente;

public class ClienteDetailModel implements ClienteDetailInterface.model {
    private ClienteDetailInterface.taskListener taskListener;

    public ClienteDetailModel(ClienteDetailInterface.taskListener taskListener) {
        this.taskListener = taskListener;
    }
}
