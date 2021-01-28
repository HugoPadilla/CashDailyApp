package com.wenitech.cashdaily.viewModel;

import androidx.lifecycle.ViewModel;

import com.wenitech.cashdaily.Data.LiveData.CajaLiveData;
import com.wenitech.cashdaily.Data.dataBase.FirestoreDataBase;
import com.wenitech.cashdaily.Data.repository.FirebaseRepository;

public class MainViewModel extends ViewModel {

    //MainRepository repository;
    FirebaseRepository firebaseRepository;

    public MainViewModel() {
        this.firebaseRepository = new FirebaseRepository();
    }

    public CajaLiveData getCaja(){
        return firebaseRepository.getCajaFirestoreRepository();
    }

    public void addMoneyOnBox(Double money){
        firebaseRepository.addMoneyOnBox(money);
    }

    public void removeMoney(Double money) {
        firebaseRepository.removeMoney(money);
    }
}

