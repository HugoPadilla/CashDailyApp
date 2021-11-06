package com.wenitech.cashdaily.framework.features.client.listClient

import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.framework.utils.UiEffect
import com.wenitech.cashdaily.framework.utils.UiEvent
import com.wenitech.cashdaily.framework.utils.UiState

class ClientContract {

    sealed class ClientEvent : UiEvent {

    }

    sealed class ClientState : UiState {
        object Error : ClientState()
        object Loading : ClientState()
        data class Success(val clientModels: List<Client>) : ClientState()
    }

    sealed class ClientEffect : UiEffect {

    }
}


