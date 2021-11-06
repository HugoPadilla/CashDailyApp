package com.wenitech.cashdaily.framework.features

import androidx.lifecycle.*
import com.wenitech.cashdaily.commons.AuthenticationStatus
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.usecases.auth.GetProfileUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProfileUserUseCase: GetProfileUserUseCase
) : ViewModel() {

    private val _stateAuth = MutableLiveData<AuthenticationStatus>()
    val stateAuth: LiveData<AuthenticationStatus> = _stateAuth

    private val _user = MutableStateFlow<Resource<User>>(Resource.Loading())
    val userModel: LiveData<Resource<User>> = _user.asLiveData()


    fun getProfile(){
        viewModelScope.launch {
            try {
                getProfileUserUseCase().collect {
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