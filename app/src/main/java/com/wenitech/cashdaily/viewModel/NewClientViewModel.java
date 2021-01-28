package com.wenitech.cashdaily.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wenitech.cashdaily.Data.dataBase.FirestoreDataBase;
import com.wenitech.cashdaily.Data.repository.FirebaseRepository;
import com.wenitech.cashdaily.Util.StartedAddNewClient;

public class NewClientViewModel extends ViewModel {

    private FirebaseRepository firebaseRepository;
    private LiveData<StartedAddNewClient> startedAddNewClientLiveData;

    public NewClientViewModel() {
        this.firebaseRepository = new FirebaseRepository();
        startedAddNewClientLiveData = firebaseRepository.startedAddNewClientLiveData;
    }

    public LiveData<StartedAddNewClient> getMessenge (){
        return startedAddNewClientLiveData;
    }

    public void addNewClient(String fullName, String idClient, String gender, String phoneNumber,
                             String city, String direction) {
        firebaseRepository.addNewClient(fullName, idClient, gender, phoneNumber, city, direction);
    }
}
