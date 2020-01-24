package com.wenitech.cashdaily.ActivityClientes.ActivityDetallesCliente;

public class ClienteDetailPresenter implements ClienteDetailInterface.presenter, ClienteDetailInterface.taskListener{

    private ClienteDetailActivity view;
    private ClienteDetailModel model;

    public ClienteDetailPresenter(ClienteDetailActivity view) {
        this.view = view;
        model = new ClienteDetailModel(this);
    }
}
