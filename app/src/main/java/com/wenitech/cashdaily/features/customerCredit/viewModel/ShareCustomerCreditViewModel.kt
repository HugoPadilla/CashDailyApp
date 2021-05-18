package com.wenitech.cashdaily.features.customerCredit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareCustomerCreditViewModel : ViewModel() {
    private val _referenciaNewCredit = MutableLiveData<String>()
    val referenciasNewCredit: LiveData<String> get() = _referenciaNewCredit

    fun setReferenciaCredit(newReferencia: String){
        _referenciaNewCredit.value = newReferencia
    }
}