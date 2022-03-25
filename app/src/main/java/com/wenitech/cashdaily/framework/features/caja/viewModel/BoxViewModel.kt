package com.wenitech.cashdaily.framework.features.caja.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.domain.usecases.caja.GetRecentMovementsUseCase
import com.wenitech.cashdaily.domain.usecases.caja.GetBoxUseCase
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
    private val getBoxUseCase: GetBoxUseCase,
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
            getBoxUseCase().collect {
                when (it) {
                    is Response.Error -> {
                        //_boxState.value = BoxContract.BoxState.Error(it.msg.toString().trim())
                    }
                    is Response.Loading -> {
                        //_boxState.value = BoxContract.BoxState.Loading
                    }
                    is Response.Success -> {
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
                    is Response.Error -> {

                    }
                    is Response.Loading -> {

                    }
                    is Response.Success -> {
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