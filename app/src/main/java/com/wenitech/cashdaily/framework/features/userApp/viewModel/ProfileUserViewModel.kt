package com.wenitech.cashdaily.framework.features.userApp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.usecases.auth.GetProfileUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileUserViewModel @Inject constructor(
    private val getProfileUserUseCase: GetProfileUserUseCase,
) : ViewModel() {

    val _userAppLivedata = MutableLiveData<Response<User>>()
    val userModelAppLiveData: LiveData<Response<User>> =
        _userAppLivedata

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            getProfileUserUseCase().collect {
                _userAppLivedata.value = it
            }
        }
    }
}