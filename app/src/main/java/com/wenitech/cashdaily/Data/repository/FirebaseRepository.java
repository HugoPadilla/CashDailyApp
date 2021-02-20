package com.wenitech.cashdaily.Data.repository;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.Data.LiveData.CajaLiveData;
import com.wenitech.cashdaily.Data.LiveData.CreditLiveData;
import com.wenitech.cashdaily.Data.model.Credito;
import com.wenitech.cashdaily.Data.model.Cuota;
import com.wenitech.cashdaily.Data.model.UserApp;
import com.wenitech.cashdaily.Data.remoteDatabase.FirestoreDataBase;
import com.wenitech.cashdaily.Util.StartedAddNewClient;

public class FirebaseRepository {

    private static FirebaseRepository instance = null;

    private final FirestoreDataBase firestoreDataBaseModel;
    public LiveData<StartedAddNewClient> startedAddNewClientLiveData;

    // Constructor
    private FirebaseRepository() {
        this.firestoreDataBaseModel = FirestoreDataBase.getInstance();
        startedAddNewClientLiveData = firestoreDataBaseModel.getStartedAddNewClient();
    }

    /**
     * Singleton Patter
     *
     * @return Instance of FirebaseRepository
     */
    public static FirebaseRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseRepository();
        }
        return instance;
    }

    // Todo: Get Method
    /**
     * get Profile of User
     *
     * @return
     */
    public LiveData<UserApp> getUserProfileRepository() {
        return firestoreDataBaseModel.getUserProfileDatabaBase();
    }

    /**
     * get Object Caja
     *
     * @param user
     * @return
     */
    public CajaLiveData getUserBoxRepository(FirebaseUser user) {
        return firestoreDataBaseModel.getCaja(user);
    }

    /**
     * get Credit To Client
     * @param referenceCredit
     * @return
     */
    public CreditLiveData getCreditClient(String referenceCredit) {
        return firestoreDataBaseModel.getCreditClient(referenceCredit);
    }

    // Todo: Set Method
    /**
     * Add money in Box
     *
     * @param money
     * @param user
     */
    public void addMoneyOnBox(Double money, FirebaseUser user) {
        firestoreDataBaseModel.agregarDineroCaja(money, user);
    }

    /**
     * Remove Money of box
     *
     * @param money
     * @param user
     */
    public void removeMoney(Double money, FirebaseUser user) {
        firestoreDataBaseModel.retirarDineroCaja(money, user);
    }

    /**
     * Add New Client
     *
     * @param fullName
     * @param idClient
     * @param gender
     * @param phoneNumber
     * @param city
     * @param direction
     */
    public void addNewClient(String fullName, String idClient, String gender, String phoneNumber, String city, String direction) {
        firestoreDataBaseModel.addNewClient(fullName, idClient, gender, phoneNumber, city, direction);
    }

    /**
     * Add new Credit of Client
     */
    public void addNewCreditOfClient(String idClient, Credito newCredit){
        firestoreDataBaseModel.addNewCreditOfClient(idClient, newCredit);
    }


    /**
     * Add New Quota
     *
     * @param cuota
     * @param idClient
     * @param referenceCredit
     * @param user
     */
    public void addNewQuota(Double cuota, String idClient, String referenceCredit, FirebaseUser user) {
        firestoreDataBaseModel.addNewQuota(cuota, idClient, referenceCredit, user);
    }
}
