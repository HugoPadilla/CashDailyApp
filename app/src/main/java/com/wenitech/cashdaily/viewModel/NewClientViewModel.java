package com.wenitech.cashdaily.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.wenitech.cashdaily.Data.repository.FirebaseRepository;
import com.wenitech.cashdaily.Util.StartedAddNewClient;

public class NewClientViewModel extends ViewModel {

    private final FirebaseRepository firebaseRepository;
    private LiveData<StartedAddNewClient> startedAddNewClientLiveData;

    public NewClientViewModel() {
        this.firebaseRepository = FirebaseRepository.getInstance();
        startedAddNewClientLiveData = firebaseRepository.startedAddNewClientLiveData;
    }

    public LiveData<StartedAddNewClient> getMessenge() {
        return startedAddNewClientLiveData;
    }

    public void addNewClient(String fullName, String idClient,
                             String gender, String phoneNumber,
                             String city, String direction) {
        firebaseRepository.addNewClient(fullName, idClient, gender, phoneNumber, city, direction);
    }
}
