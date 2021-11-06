package com.wenitech.cashdaily.framework.features.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.entities.Ruta
import com.wenitech.cashdaily.domain.usecases.caja.GetUserBoxUseCase
import com.wenitech.cashdaily.domain.usecases.route.GetRoutesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getUserBoxUseCase: GetUserBoxUseCase,
    private val getRoutesUseCase: GetRoutesUseCase,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(Box())
    val homeUiState: StateFlow<Box> = _homeUiState

    private val _routeUiState = MutableStateFlow<List<Ruta>>(listOf())
    val routeUiState: StateFlow<List<Ruta>> = _routeUiState

    init {
        getUserBox()
        getRoutes()
    }

    private fun getUserBox() {
        val uid = auth.currentUser?.uid
        if (!uid.isNullOrEmpty()) {
            viewModelScope.launch {
                getUserBoxUseCase(uid).collect {
                    //_homeUiState.value = it
                    when (it) {
                        is com.wenitech.cashdaily.domain.common.Resource.Failure -> {

                        }
                        is com.wenitech.cashdaily.domain.common.Resource.Loading -> {

                        }
                        is com.wenitech.cashdaily.domain.common.Resource.Success -> {
                            _homeUiState.value = it.data
                        }
                    }
                }
            }
        } else {
            //_homeUiState.value =
        }
    }

    private fun getRoutes() {
        viewModelScope.launch {
            getRoutesUseCase().collect {
                //_routeUiState.value = it
                when (it) {
                    is com.wenitech.cashdaily.domain.common.Resource.Failure -> {

                    }
                    is com.wenitech.cashdaily.domain.common.Resource.Loading -> {

                    }
                    is com.wenitech.cashdaily.domain.common.Resource.Success -> {
                        _routeUiState.value = it.data
                    }
                }
            }
        }
    }

}