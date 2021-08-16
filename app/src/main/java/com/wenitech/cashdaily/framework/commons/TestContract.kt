package com.wenitech.cashdaily.framework.commons

import com.wenitech.cashdaily.framework.utils.UiEffect
import com.wenitech.cashdaily.framework.utils.UiEvent
import com.wenitech.cashdaily.framework.utils.UiState

class TestContract {
    sealed class Event : UiEvent {
        object OnRandomNumberClicked : Event()
        object OnShowToastClicked : Event()
    }

    data class State(
        val randomNumberState: RandomNumberState
    ) : UiState

    sealed class RandomNumberState {
        object Idle : RandomNumberState()
        object Loading : RandomNumberState()
        data class Success(val number: Int) : RandomNumberState()
    }

    sealed class Effect : UiEffect {
        object ShowToast : Effect()
    }
}