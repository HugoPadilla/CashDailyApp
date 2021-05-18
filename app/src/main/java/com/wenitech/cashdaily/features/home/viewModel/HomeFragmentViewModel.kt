package com.wenitech.cashdaily.features.home.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.wenitech.cashdaily.data.model.Caja
import com.wenitech.cashdaily.data.model.User
import com.wenitech.cashdaily.domain.interaction.caja.GetUserAppBoxUseCase
import com.wenitech.cashdaily.domain.interaction.auth.GetProfileUserAppUseCase

class HomeFragmentViewModel @ViewModelInject constructor(
        private val getUserAppBoxUseCase: GetUserAppBoxUseCase,
        private val getProfileUserAppUseCase: GetProfileUserAppUseCase
) : ViewModel() {

    var _profileUser: MutableLiveData<User> = MutableLiveData()
    val profileUser: LiveData<User>
        get() = _profileUser

    init {
        getProfileUserApp()
    }

    val cajaLiveData = getUserAppBoxUseCase.execute().asLiveData()

    fun getProfileUserApp() {
        /*getProfileUserAppUseCase.execute().observeForever(Observer {
            _profileUserApp.value = it
        })*/
    }

}