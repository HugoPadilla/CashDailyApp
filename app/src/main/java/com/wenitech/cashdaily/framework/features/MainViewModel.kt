package com.wenitech.cashdaily.framework.features

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wenitech.cashdaily.commons.AuthenticationStatus
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.usecases.auth.GetProfileUserAppUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val getProfileUserAppUseCase: GetProfileUserAppUseCase
) : ViewModel() {

    private val _stateAuth = MutableLiveData<AuthenticationStatus>()
    val stateAuth: LiveData<AuthenticationStatus> = _stateAuth

    private val _user = MutableStateFlow<Resource<User>>(Resource.Loading())
    val user: LiveData<Resource<User>> = _user.asLiveData()


    fun getProfile(uid: String){
        viewModelScope.launch {
            try {
                getProfileUserAppUseCase(uid).collect {
                    _user.value = it
                }
            } catch (e: Throwable) {
                _user.value = Resource.Failure(e)
            }
        }
    }

    fun setStateAuth( status: AuthenticationStatus){
        _stateAuth.value = status
    }


}