package com.wenitech.cashdaily.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.wenitech.cashdaily.Data.model.UserApp;
import com.wenitech.cashdaily.Data.repository.FirebaseRepository;

public class ProfileUserViewModel extends ViewModel {

    private FirebaseRepository firebaseRepository;
    public LiveData<UserApp> userAppLiveData;

    /**
     * Constructor
     */
    public ProfileUserViewModel() {
        this.firebaseRepository = FirebaseRepository.getInstance();
    }

    public void getUserProfile(){
        userAppLiveData = firebaseRepository.getUserProfileRepository();
    }
}