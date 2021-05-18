package com.wenitech.cashdaily.features.userApp.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.wenitech.cashdaily.domain.interaction.auth.GetProfileUserAppUseCase
import com.wenitech.cashdaily.core.ResourceAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class ProfileUserViewModel @ViewModelInject constructor(
        private val getProfileUserAppUseCase: GetProfileUserAppUseCase,
) : ViewModel() {

    val userAppLiveData = liveData(Dispatchers.IO) {
        emit(ResourceAuth.loading(null))
        try {
            getProfileUserAppUseCase.execute().collect {
                emit(it)
            }
        } catch (e: Exception) {
            emit(ResourceAuth.error(e.message.toString(),null))
        }
    }
}