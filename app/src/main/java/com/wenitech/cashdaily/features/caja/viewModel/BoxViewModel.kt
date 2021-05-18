package com.wenitech.cashdaily.features.caja.viewModel


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseUser
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.model.Caja
import com.wenitech.cashdaily.domain.interaction.caja.GetUserAppBoxUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BoxViewModel @ViewModelInject constructor(
        private val getUserAppBoxUseCase: GetUserAppBoxUseCase,
) : ViewModel() {

    private val _resourceAddMoneyOnBox = MutableLiveData<Resource<String>>()
    val resourceAddMoneyOnBox get() = _resourceAddMoneyOnBox

    private val _resourceRemoveMoneyOnBox = MutableLiveData<Resource<String>>()
    val resourceRemoveMoneyOnBox get() = _resourceRemoveMoneyOnBox

    val boxLiveData = getUserAppBoxUseCase.execute().asLiveData()
    val getRecentMovements = getUserAppBoxUseCase.getRecentCashMovements().asLiveData()


    fun addMoneyOnBox(money: Double) {
        viewModelScope.launch {
            getUserAppBoxUseCase.saveMoneyOnBox(money).collect {
                _resourceAddMoneyOnBox.value = it
            }
        }
    }

    fun removeMoney(money: Double) {
        viewModelScope.launch {
            getUserAppBoxUseCase.removeMoneyOnBox(money).collect{
                _resourceRemoveMoneyOnBox.value = it
            }
        }
    }


}