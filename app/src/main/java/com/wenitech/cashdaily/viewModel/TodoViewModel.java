package com.wenitech.cashdaily.viewModel;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.Data.LiveData.CajaLiveData;
import com.wenitech.cashdaily.Data.model.Cuota;
import com.wenitech.cashdaily.Data.repository.FirebaseRepository;

public class TodoViewModel extends ViewModel {

    public CajaLiveData cajaLiveData = null;

    //MainRepository repository;
    FirebaseRepository firebaseRepository;

    // Constructor
    public TodoViewModel() {
        this.firebaseRepository = FirebaseRepository.getInstance();
    }

    /**
     * Get Object Box
     *
     * @param user
     */
    public void getCaja(FirebaseUser user) {
        cajaLiveData = firebaseRepository.getUserBoxRepository(user);
    }

    public void addMoneyOnBox(Double money, FirebaseUser user) {
        firebaseRepository.addMoneyOnBox(money, user);
    }

    public void removeMoney(Double money, FirebaseUser user) {
        firebaseRepository.removeMoney(money, user);
    }

    public void addNewQuota (Double cuota, String referenceClient, String referenceCredit, FirebaseUser user){
        firebaseRepository.addNewQuota(cuota, referenceClient, referenceCredit, user );
    }
}

