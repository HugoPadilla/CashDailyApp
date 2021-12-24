package com.wenitech.cashdaily.framework.features.credit.registerCredit

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.usecases.credit.SaveNewCreditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RegisterCreditViewModel @Inject constructor(
    private val saveNewCreditUseCase: SaveNewCreditUseCase,
) : ViewModel() {

    private val customDateFormat = SimpleDateFormat("dd MMMM yyyy hh:mm a", Locale.getDefault())
    private val numberFormat = NumberFormat.getCurrencyInstance()

    private val modalityOptions = listOf(
        ModalityCredit.Diario,
        ModalityCredit.Semanal,
        ModalityCredit.Quincenal,
        ModalityCredit.Mensual
    )

    private val _uiState = MutableStateFlow(RegisterCreditUiState())
    val uiState = _uiState.asStateFlow()

    private val _idClient = MutableStateFlow("")

    init {
        val currentDate = customDateFormat.format(Date())
        _uiState.value = _uiState.value.copy(dateCredit = currentDate)

        _uiState.value = _uiState.value.copy(modalityCreditOptions = modalityOptions)
    }

    fun setIdClient(idClient: String) {
        _idClient.value = idClient
    }

    fun onDismissDialog() {
        _uiState.value = _uiState.value.copy(
            isFailedResult = false,
            isLoadingResult = false,
            isSuccessResult = false
        )
    }

    fun onSuccessDialog() {
        _uiState.value = _uiState.value.copy(
            creditValue = "",
            creditPercent = "",
            amountFest = "",
            creditQuotaValue = "",
            buttonEnable = false
        )
    }

    fun setDateCredit(date: String) {
        // Todo: Set date
    }

    fun setCreditValue(creditValue: String) {
        _uiState.value = _uiState.value.copy(creditValue = creditValue)
        isFormCreditValid
        calculateCredit()
    }

    fun setCreditPercent(percent: String) {
        _uiState.value = _uiState.value.copy(creditPercent = percent)
        isFormCreditValid
        calculateCredit()
    }

    fun setOptionSelected(optionSelect: ModalityOptions) {
        _uiState.value = _uiState.value.copy(modalitySelected = optionSelect)

        when (optionSelect) {
            ModalityOptions.MODALIDAD_DIARIO -> {
                _uiState.value = _uiState.value.copy(amountFestLabelText = "Dias de plazo")
                _uiState.value = _uiState.value.copy(amountFest = "", creditQuotaValue = "")
            }
            ModalityOptions.MODALIDAD_SEMANAL -> {
                _uiState.value = _uiState.value.copy(amountFestLabelText = "Semanas de plazo")
                _uiState.value = _uiState.value.copy(amountFest = "", creditQuotaValue = "")
            }
            ModalityOptions.MODALIDAD_QINCENAL -> {
                _uiState.value = _uiState.value.copy(amountFestLabelText = "Quincenas de plazo")
                _uiState.value = _uiState.value.copy(amountFest = "", creditQuotaValue = "")
            }
            ModalityOptions.MODALIDAD_MENSUAL -> {
                _uiState.value = _uiState.value.copy(amountFestLabelText = "Meses de plazo")
                _uiState.value = _uiState.value.copy(amountFest = "", creditQuotaValue = "")
            }
        }

    }

    fun setAmountFees(amountFees: String) {
        _uiState.value = _uiState.value.copy(amountFest = amountFees)
        isFormCreditValid
        calculateCredit()
    }

    fun setCreditQuotaValue(quotaValue: String) {
        _uiState.value = _uiState.value.copy(creditQuotaValue = quotaValue)
        isFormCreditValid
    }

    /**
     * Llama al usecase para guardar un nuevo credito
     */
    fun saveCustomerCreditFromClient() {

        if (isFormCreditValid) {
            val newCredit = buildCreditObjet()
            viewModelScope.launch {
                saveNewCreditUseCase(_idClient.value, newCredit).collect {
                    when (it) {
                        is Response.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isSuccessResult = false,
                                isLoadingResult = false,
                                isFailedResult = true
                            )
                        }
                        is Response.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                isSuccessResult = false,
                                isLoadingResult = true,
                                isFailedResult = false
                            )
                        }
                        is Response.Success -> {
                            _uiState.value = _uiState.value.copy(
                                isSuccessResult = true,
                                isLoadingResult = false,
                                isFailedResult = false
                            )
                        }
                    }
                }
            }

        }
    }

    private fun calculateCredit() {
        val creditValue = _uiState.value.creditValue
        val creditPercent = _uiState.value.creditPercent
        val amountFees = _uiState.value.amountFest

        val value = if (creditValue.isNotEmpty()) creditValue.toDouble() else 0.0
        val percent = if (creditPercent.isNotEmpty()) creditPercent.toDouble() / 100 else 0.0
        val amountFeesValue = if (amountFees.isNotEmpty()) amountFees.toDouble() else 0.0

        val resultTotalCredit = value + (value * percent)
        val resultQuotaValue =
            if (amountFees.isNotEmpty()) resultTotalCredit / amountFeesValue else 0.0

        _uiState.value = _uiState.value.copy(
            totalCredit = numberFormat.format(resultTotalCredit),
            creditQuotaValue = numberFormat.format(resultQuotaValue)
        )
    }

    // Metodo util
    private fun buildCreditObjet(): Credit {

        val newCredit = Credit()
        val total = numberFormat.parse(_uiState.value.totalCredit).toDouble()
        val quotaValue = numberFormat.parse(_uiState.value.creditQuotaValue).toDouble()

        return newCredit.apply {
            dateCreation = customDateFormat.parse(_uiState.value.dateCredit)
            creditValue = _uiState.value.creditValue.toDouble()
            percentage = _uiState.value.creditPercent.toInt()
            creditTotal = total
            creditDebt = total
            creditQuotaValue = quotaValue
        }

    }

    private val isFormCreditValid: Boolean
        get() {

            val creditDate = _uiState.value.dateCredit
            val creditValue = _uiState.value.creditValue
            val creditPercent = _uiState.value.creditPercent
            val amountFees = _uiState.value.amountFest
            val creditQuotaValues = _uiState.value.creditQuotaValue
            _uiState.value = _uiState.value.copy(buttonEnable = false)
            when {
                TextUtils.isEmpty(creditDate) -> {
                    _uiState.value =
                        _uiState.value.copy(dateCreditMessageError = "Seleciona una fecha")
                    return false
                }

                TextUtils.isEmpty(creditValue) -> {
                    _uiState.value =
                        _uiState.value.copy(creditValueMessageError = "Escribe un valor")
                    return false
                }
                TextUtils.isEmpty(creditPercent) -> {
                    _uiState.value =
                        _uiState.value.copy(creditPercentMessageError = "Establece un porcentaje")
                    return false
                }
                TextUtils.isEmpty(amountFees) -> {
                    _uiState.value =
                        _uiState.value.copy(amountFestMessageError = "Escribe un valor")
                    return false

                }
                TextUtils.isEmpty(creditQuotaValues) -> {
                    _uiState.value =
                        _uiState.value.copy(creditQuotaValueMessageError = "Establece valor de cuota")
                    return false

                }
                else -> {
                    _uiState.value = _uiState.value.copy(
                        dateCreditMessageError = null,
                        creditValueMessageError = null,
                        creditPercentMessageError = null,
                        amountFestMessageError = null,
                        creditQuotaValueMessageError = null,
                        buttonEnable = true
                    )
                    return true
                }
            }
        }

}