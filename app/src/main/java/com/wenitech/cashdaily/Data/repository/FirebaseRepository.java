package com.wenitech.cashdaily.Data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wenitech.cashdaily.Data.LiveData.CajaLiveData;
import com.wenitech.cashdaily.Data.dataBase.FirestoreDataBase;
import com.wenitech.cashdaily.Util.StartedAddNewClient;

public class FirebaseRepository {

    private final FirestoreDataBase firestoreDataBaseModel;
    public LiveData<StartedAddNewClient> startedAddNewClientLiveData;

    public FirebaseRepository() {
        this.firestoreDataBaseModel = FirestoreDataBase.getInstance();
        startedAddNewClientLiveData = firestoreDataBaseModel.getStartedAddNewClient();
    }

    public CajaLiveData getCajaFirestoreRepository() {
        return firestoreDataBaseModel.getCaja();
    }

    public void addMoneyOnBox(Double money) {
        firestoreDataBaseModel.agregarDineroCaja(money);
    }

    public void removeMoney(Double money) {
        firestoreDataBaseModel.retirarDineroCaja(money);
    }

    public void addNewClient(String fullName, String idClient, String gender, String phoneNumber, String city, String direction) {
        firestoreDataBaseModel.addNewClient(fullName, idClient, gender, phoneNumber, city, direction);
    }
}
