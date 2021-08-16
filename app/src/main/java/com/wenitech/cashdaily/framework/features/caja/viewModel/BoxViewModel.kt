package com.wenitech.cashdaily.framework.features.caja.viewModel


import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.usecases.caja.GetRecentMovementsUseCase
import com.wenitech.cashdaily.domain.usecases.caja.GetUserBoxUseCase
import com.wenitech.cashdaily.domain.usecases.caja.RemoveMoneyOnBoxUseCase
import com.wenitech.cashdaily.domain.usecases.caja.SaveMoneyOnBoxUseCase
import com.wenitech.cashdaily.framework.features.caja.BoxContract
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BoxViewModel @ViewModelInject constructor(
    private val auth: FirebaseAuth,
    private val getUserBoxUseCase: GetUserBoxUseCase,
    private val getRecentMovementsUseCaseUseCase: GetRecentMovementsUseCase,
    private val saveMoneyOnBoxUseCase: SaveMoneyOnBoxUseCase,
    private val removeMoneyOnBoxUseCase: RemoveMoneyOnBoxUseCase
) : ViewModel() {

    private val _boxState = MutableLiveData<BoxContract.BoxState>()
    val boxState: LiveData<BoxContract.BoxState> = _boxState

    private val _cashMovement = MutableLiveData<BoxContract.CashState>()
    val cashMovement: LiveData<BoxContract.CashState> = _cashMovement


    fun process(event: BoxContract.BoxEvent) {
        when (event) {
            is BoxContract.BoxEvent.AddMoneyClicked -> addMoneyOnBox(event.money, event.descriptions)
            is BoxContract.BoxEvent.RemoveMoneyClicked -> removeMoney(event.money, event.descriptions)
        }
    }

    init {
        fetchBox()
        fetchMovement()
    }

    private fun fetchBox() {
        viewModelScope.launch {
            val uid = auth.uid
            if (!uid.isNullOrEmpty()) {
                getUserBoxUseCase(uid).collect {
                    when (it) {
                        is Resource.Failure -> {
                            _boxState.value = BoxContract.BoxState.Error(it.msg.toString().trim())
                        }
                        is Resource.Loading -> {
                            _boxState.value = BoxContract.BoxState.Loading
                        }
                        is Resource.Success -> {
                            _boxState.value = BoxContract.BoxState.Success(it.data)
                        }
                    }
                }
            } else {
                _boxState.value = BoxContract.BoxState.Success(Box())
            }
        }
    }

    private fun fetchMovement() {
        viewModelScope.launch {
            getRecentMovementsUseCaseUseCase(auth.uid.toString()).collect {
                when (it) {
                    is Resource.Failure -> _cashMovement.value =
                        BoxContract.CashState.Error(it.msg.toString())
                    is Resource.Loading -> _cashMovement.value =
                        BoxContract.CashState.Loading
                    is Resource.Success -> {
                        _cashMovement.value = BoxContract.CashState.Success(it.data)
                        Log.d("FETCHMOVEMENT", "fetchMovement: ${it.data}")
                    }
                }
            }
        }
    }

    private fun addMoneyOnBox(money: Double, description: String) {
        viewModelScope.launch {
            saveMoneyOnBoxUseCase(auth.uid.toString(), money, description).collect {

            }
        }
    }

    private fun removeMoney(money: Double, description: String) {
        viewModelScope.launch {
            removeMoneyOnBoxUseCase(auth.uid.toString(), money, description).collect {

            }
        }
    }

}