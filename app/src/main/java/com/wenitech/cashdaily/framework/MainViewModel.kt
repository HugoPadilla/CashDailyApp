package com.wenitech.cashdaily.framework

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.usecases.auth.GetAuthStateUseCase
import com.wenitech.cashdaily.domain.usecases.auth.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authStateUseCase: GetAuthStateUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    val getAuthState = mutableStateOf(false)

    init {
        getAuthState()
    }

    private fun getAuthState() {
        viewModelScope.launch {
            authStateUseCase().collect {
                getAuthState.value = it
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase().collect {
                when (it) {
                    is ResultAuth.Collision -> {
                    }
                    is ResultAuth.Failed -> {
                    }
                    ResultAuth.Loading -> {
                    }
                    is ResultAuth.Success -> {
                    }
                }
            }
        }
    }
}
