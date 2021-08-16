package com.wenitech.cashdaily.framework.features.userApp.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.usecases.auth.GetProfileUserAppUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileUserViewModel @ViewModelInject constructor(
    private val auth: FirebaseAuth,
    private val getProfileUserAppUseCase: GetProfileUserAppUseCase,
) : ViewModel() {

    val _userAppLivedata = MutableLiveData<Resource<User>>()
    val userAppLiveData: LiveData<Resource<User>> = _userAppLivedata

    init {
        getProfile()
    }

    private fun getProfile() {
        val uid = auth.currentUser?.uid
        if (!uid.isNullOrEmpty()) {
            viewModelScope.launch {
                getProfileUserAppUseCase(uid).collect {
                    _userAppLivedata.value = it
                }
            }
        } else {
            // No se ha iniciado sesion
        }
    }


}