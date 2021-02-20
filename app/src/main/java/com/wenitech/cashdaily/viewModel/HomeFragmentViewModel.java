package com.wenitech.cashdaily.viewModel;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.Data.LiveData.CajaLiveData;
import com.wenitech.cashdaily.Data.repository.FirebaseRepository;

public class HomeFragmentViewModel extends ViewModel {

    public CajaLiveData cajaLiveData = null;

    private FirebaseRepository firebaseRepository;

    public HomeFragmentViewModel() {
        this.firebaseRepository = FirebaseRepository.getInstance();
    }

    public void getUserBox(FirebaseUser user) {
        cajaLiveData = firebaseRepository.getUserBoxRepository(user);
    }
}