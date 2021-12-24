package com.wenitech.cashdaily.framework.features.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Response
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
class HomeViewModel @Inject constructor(
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

        viewModelScope.launch {
            getUserBoxUseCase().collect {
                when (it) {
                    is Response.Error -> {

                    }
                    is Response.Loading -> {

                    }
                    is Response.Success -> {
                        _homeUiState.value = it.data
                    }
                }
            }
        }
    }

    private fun getRoutes() {
        viewModelScope.launch {
            getRoutesUseCase().collect {
                when (it) {
                    is Response.Error -> {

                    }
                    is Response.Loading -> {

                    }
                    is Response.Success -> {
                        _routeUiState.value = it.data
                    }
                }
            }
        }
    }

}