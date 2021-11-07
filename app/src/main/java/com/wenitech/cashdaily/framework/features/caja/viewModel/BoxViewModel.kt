package com.wenitech.cashdaily.framework.features.caja.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.domain.usecases.caja.GetRecentMovementsUseCase
import com.wenitech.cashdaily.domain.usecases.caja.GetUserBoxUseCase
import com.wenitech.cashdaily.domain.usecases.caja.RemoveMoneyOnBoxUseCase
import com.wenitech.cashdaily.domain.usecases.caja.SaveMoneyOnBoxUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxViewModel @Inject constructor(
    private val getUserBoxUseCase: GetUserBoxUseCase,
    private val getRecentMovementsUseCaseUseCase: GetRecentMovementsUseCase,
    private val saveMoneyOnBoxUseCase: SaveMoneyOnBoxUseCase,
    private val removeMoneyOnBoxUseCase: RemoveMoneyOnBoxUseCase
) : ViewModel() {

    private val _boxState = MutableStateFlow(Box())
    val boxModelState: StateFlow<Box> = _boxState

    private val _cashMovement = MutableStateFlow<List<CashTransactions>>(listOf())
    val cashMovementModel: StateFlow<List<CashTransactions>> = _cashMovement

    init {
        fetchBox()
        fetchMovement()
    }

    /**
     * Obtiene la informacion de caja
     */
    private fun fetchBox() {
        viewModelScope.launch {
            getUserBoxUseCase().collect {
                when (it) {
                    is Resource.Failure -> {
                        //_boxState.value = BoxContract.BoxState.Error(it.msg.toString().trim())
                    }
                    is Resource.Loading -> {
                        //_boxState.value = BoxContract.BoxState.Loading
                    }
                    is Resource.Success -> {
                        _boxState.value = it.data
                    }
                }
            }
        }
    }

    /**
     * Obtine los movimientos recientes
     */
    private fun fetchMovement() {
        viewModelScope.launch {
            getRecentMovementsUseCaseUseCase().collect {
                when (it) {
                    is Resource.Failure -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        _cashMovement.value = it.data
                        Log.d("FETCHMOVEMENT", "fetchMovement: ${it.data}")
                    }
                }
            }
        }
    }

    /**
     * Agregar mas dinero a la caja
     */
    fun addMoneyOnBox(money: Double, description: String) {
        viewModelScope.launch {
            saveMoneyOnBoxUseCase(money, description).collect {

            }
        }
    }

    /**
     * Retirar dinero de la caja
     */
    fun removeMoney(money: Double, description: String) {
        viewModelScope.launch {
            removeMoneyOnBoxUseCase(money, description).collect {

            }
        }
    }

}