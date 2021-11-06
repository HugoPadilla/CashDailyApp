package com.wenitech.cashdaily.framework.features.client.customerCredit

import com.wenitech.cashdaily.data.entities.CreditModel
import com.wenitech.cashdaily.data.entities.QuotaModel
import com.wenitech.cashdaily.framework.features.client.customerCredit.model.ClientParcelable

class CustomerContract {

    sealed class CustomerEvent : MviIntent {
        data class getCustomerCredit(val idClient: String, val idCredit: String) : CustomerEvent()
        data class SetClientParzelable(val clientParcelable: ClientParcelable) : CustomerEvent()
        data class SetNewQuota(val value: Double) : CustomerEvent()
    }

    enum class LoadState {
        IDLE,
        LOADING,
        LOADED,
        ERROR
    }

    data class CustomerState(
        val loadState: LoadState,
        val creditModel: CreditModel,
        val errorMessage: String
    ) : State {
        companion object {
            val initial = CustomerState(
                loadState = LoadState.IDLE,
                creditModel = CreditModel(),
                errorMessage = ""
            )
        }
    }

    sealed class QuotaCustomerState {
        object Loading : QuotaCustomerState()
        object Error : QuotaCustomerState()
        object Empty : QuotaCustomerState()
        data class Success(val listQuotaModel: List<QuotaModel>) : QuotaCustomerState()
    }

}