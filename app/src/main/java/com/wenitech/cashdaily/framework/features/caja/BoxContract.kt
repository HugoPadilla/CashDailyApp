package com.wenitech.cashdaily.framework.features.caja

import com.wenitech.cashdaily.domain.entities.Box
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.framework.utils.UiEffect
import com.wenitech.cashdaily.framework.utils.UiEvent
import com.wenitech.cashdaily.framework.utils.UiState

class BoxContract {

    sealed class BoxEvent : UiEvent {
        class AddMoneyClicked(val money: Double, val descriptions: String) : BoxEvent()
        class RemoveMoneyClicked(val money: Double, val descriptions: String) : BoxEvent()
    }

    sealed class BoxState : UiState {
        data class Error(val msg: String) : BoxState()
        data class Success(val box: Box) : BoxState()
        object Loading : BoxState()
    }

    sealed class CashState {
        data class Error(val msg: String) : CashState()
        data class Success(val cashMovement: List<CashTransactions>) : CashState()
        object Loading : CashState()
    }

    sealed class BoxEffect : UiEffect {
        data class ShowToast(val msg: String) : BoxEffect()
    }
}

