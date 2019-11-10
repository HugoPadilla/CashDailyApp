package com.wenitech.cashdaily.ClienteDetail;

public class ClienteDetailModel implements ClienteDetailInterface.model {
    private ClienteDetailInterface.taskListener taskListener;

    public ClienteDetailModel(ClienteDetailInterface.taskListener taskListener) {
        this.taskListener = taskListener;
    }
}
