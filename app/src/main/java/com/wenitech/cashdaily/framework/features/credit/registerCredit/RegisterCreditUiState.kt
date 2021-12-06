package com.wenitech.cashdaily.framework.features.credit.registerCredit

data class RegisterCreditUiState(
    val isSuccessResult: Boolean = false,
    val isLoadingResult: Boolean = false,
    val isFailedResult: Boolean = false,
    val totalCredit: String = "0",
    val dateCredit: String = "",
    val dateCreditMessageError: String? = null,
    val creditValue: String = "",
    val creditValueMessageError: String? = null,
    val creditPercent: String = "",
    val creditPercentMessageError: String? = null,
    val modalityCreditOptions: List<ModalityCredit> = listOf(),
    val modalitySelected: ModalityOptions = ModalityOptions.MODALIDAD_DIARIO,
    val amountFest: String = "",
    val amountFestMessageError: String? = null,
    val amountFestLabelText: String = "Dias de plazo",
    val creditQuotaValue: String = "",
    val creditQuotaValueMessageError: String? = null,
    val buttonEnable: Boolean = false
)