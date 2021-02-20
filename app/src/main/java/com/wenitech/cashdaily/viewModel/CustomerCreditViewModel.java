package com.wenitech.cashdaily.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.Data.model.Credito;
import com.wenitech.cashdaily.Data.repository.FirebaseRepository;

public class CustomerCreditViewModel extends ViewModel {

    private FirebaseRepository firebaseRepository;

    public LiveData<Credito> creditLiveData;
    
    // Constructor
    public CustomerCreditViewModel() {
        this.firebaseRepository = FirebaseRepository.getInstance();
    }

    // Todo: Get Method
    public void getCreditClient(String referenceCredit){
        creditLiveData = firebaseRepository.getCreditClient(referenceCredit);
    }

    // Todo: Set Method
    public void addNewCreditOfClient(String idClient, Credito newCredito){
        firebaseRepository.addNewCreditOfClient(idClient, newCredito);
    }

    public void addNewQuota(Double newQuota, String idClient, String referenceCredit, FirebaseUser user) {
        firebaseRepository.addNewQuota(newQuota,idClient,referenceCredit,user);
    }
}
