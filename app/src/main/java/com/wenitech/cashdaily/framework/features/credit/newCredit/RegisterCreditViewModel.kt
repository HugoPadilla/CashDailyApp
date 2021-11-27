package com.wenitech.cashdaily.framework.features.credit.newCredit

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.usecases.credit.SaveNewCreditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RegisterCreditViewModel @Inject constructor(
    private val saveNewCreditUseCase: SaveNewCreditUseCase,
) : ViewModel() {

    private val _modalityOptions =
        MutableStateFlow(listOf("Diario", "Semanal", "Quincenal", "Mensual"))
    val modalityOptions: StateFlow<List<String>> get() = _modalityOptions

    private val _idClient = MutableStateFlow("")
    fun setIdClient(idClient: String){
        _idClient.value = idClient
    }

    // Todo: Reemplazar por un dataClass que represente el estado de la UI
    private val _resultSaveNewCredit = MutableLiveData<Resource<String>>()
    val resultSaveNewCredit: MutableLiveData<Resource<String>> get() = _resultSaveNewCredit

    private val _totalCredit = MutableStateFlow("")
    val totalCredit: StateFlow<String> get() = _totalCredit
    private var _dateCredit = MutableStateFlow("")
    val dateCredit: StateFlow<String> get() = _dateCredit
    private val _valueCredit = MutableStateFlow("")
    val valueCredit: StateFlow<String> get() = _valueCredit
    private val _percentCredit = MutableStateFlow("")
    val percentCredit: StateFlow<String> get() = _percentCredit
    private val _modalitySelected = MutableStateFlow(modalityOptions.value[0])
    val modalitySelected: StateFlow<String> get() = _modalitySelected
    fun onModalityOptionSelected(optionSelect: String) {
        _modalitySelected.value = optionSelect
    }

    private val _amountFees = MutableStateFlow("")
    val amountFees: StateFlow<String> get() = _amountFees
    private val _quotaValue = MutableStateFlow("")
    val quotaValue: StateFlow<String> get() = _quotaValue

    // MutableLiveData messageError views
    private var _dateCreditMessageError = MutableStateFlow<String?>(null)
    val dateCreditMessageError: StateFlow<String?> = _dateCreditMessageError
    private var _valueCreditMessageError = MutableStateFlow<String?>(null)
    val valueCreditMessageError: StateFlow<String?> = _valueCreditMessageError
    private var _percentCreditMessageError = MutableStateFlow<String?>(null)
    val percentCreditMessageError: StateFlow<String?> = _percentCreditMessageError
    private var _amountFeesMessageError = MutableStateFlow<String?>(null)
    val amountFeesMessageError: StateFlow<String?> = _amountFeesMessageError
    private var _valueQuotaCreditMessageError = MutableStateFlow<String?>(null)
    val valueQuotaMessageError: StateFlow<String?> = _valueQuotaCreditMessageError

    init {
        initDateForEditText()
    }

    private fun initDateForEditText() {
        val date = Date()
        val formatFecha = SimpleDateFormat("dd MMMM yyyy")
        val formatHora = SimpleDateFormat("hh:mm a")
        val fecha = formatFecha.format(date)
        val hora = formatHora.format(date)
        _dateCredit.value = "$fecha $hora"
    }

    /**
     * Llama al usecase para guardar un nuevo credito
     */
    fun saveCustomerCreditFromClient() {

        if (isFormCreditValid && isFormModalityCreditValid) {
            val newCredit = buildCreditObjet()
            viewModelScope.launch {
                saveNewCreditUseCase(_idClient.value, newCredit).collect {
                    when (it) {
                        is Resource.Failure -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {

                        }
                    }
                }
            }

        }
    }


    fun changerArgumentSafeArg(idClientString: String) {
        _idClient.value = idClientString
    }

    // Metodo util
    private fun buildCreditObjet(): Credit {

        val newCredit = Credit()

        newCredit.apply {
            dateCreation = Date(_dateCredit.value)
            creditValue = _valueCredit.value.toDouble()
            percentage = _percentCredit.value.toInt()
            creditTotal = _totalCredit.value.toDouble()
            creditDebt = _totalCredit.value.toDouble()
        }
        _resultSaveNewCredit.value = Resource.Loading()

        /*when (_radioGrupSelecte.value) {
            R.id.radio_button_nuevo_credito_diario -> {
                newCredit.paymentMethod = PaymentMethodEnum.Daily.name
                newCredit.amountFees = _plazoCredit.value!!.trim { it <= ' ' }.toInt()
                newCredit.creditQuotaValue = _valueCuotaCredit.value!!.trim { it <= ' ' }.toDouble()

                // Modificar en funcion del selecionado por el usuario
                newCredit.isChargedOnSaturday = false
                newCredit.isChargedOnSunday = false

                // Generar la proxima fecha de cobro cuado es diarios
                val dateFecha = Date()
                val calendar = Calendar.getInstance()
                calendar.time = dateFecha
                calendar.add(Calendar.DAY_OF_YEAR, 1)
                newCredit.timestampNextPayment = Timestamp(calendar.time)
            }
            R.id.radio_button_nuevo_credito_semanal -> {
                newCredit.paymentMethod = PaymentMethodEnum.Weekly.name
                newCredit.amountFees = _plazoCredit.value!!.trim { it <= ' ' }.toInt()
                newCredit.creditQuotaValue = _valueCuotaCredit.value!!.trim { it <= ' ' }.toDouble()
                newCredit.isChargedOnSaturday = false
                newCredit.isChargedOnSunday = false
            }
            R.id.radio_button_nuevo_credito_quincenal -> {
                newCredit.paymentMethod = PaymentMethodEnum.Biweekly.name
                newCredit.amountFees = _plazoCredit.value!!.trim { it <= ' ' }.toInt()
                newCredit.creditQuotaValue = _valueCuotaCredit.value!!.trim { it <= ' ' }.toDouble()
                newCredit.isChargedOnSaturday = false
                newCredit.isChargedOnSunday = false
            }
            R.id.radio_button_nuevo_credito_mensual -> {
                newCredit.paymentMethod = PaymentMethodEnum.Monthly.name
                newCredit.amountFees = _plazoCredit.value!!.trim { it <= ' ' }.toInt()
                newCredit.creditQuotaValue = _valueCuotaCredit.value!!.trim { it <= ' ' }.toDouble()
                newCredit.isChargedOnSaturday = false
                newCredit.isChargedOnSunday = false
            }
        }*/


        return newCredit
    }


    private val isFormCreditValid: Boolean
        get() {
            when {
                TextUtils.isEmpty(_dateCredit.value) -> {
                    _dateCreditMessageError.value = "Seleciona una fecha"
                    return false
                }

                TextUtils.isEmpty(_valueCredit.value) -> {
                    _valueCreditMessageError.value = "Escribe un valor"
                    return false
                }
                TextUtils.isEmpty(_percentCredit.value) -> {
                    _percentCreditMessageError.value = "Establece un porcentaje"
                    return false
                }

                else -> {
                    _dateCreditMessageError.value = ""
                    _valueCreditMessageError.value = ""
                    _percentCreditMessageError.value = ""
                    return true
                }
            }
        }

    /**
     * @return
     */
    private val isFormModalityCreditValid: Boolean
        get() {
            var isValid = true
            if (TextUtils.isEmpty(_amountFees.value)) {
                _amountFeesMessageError.value = "Estable una plazo"
                isValid = false
            } else if (TextUtils.isEmpty(quotaValue.value)) {
                _valueQuotaCreditMessageError.value = "Valor cuota"
                isValid = false
            }
            return isValid
        }


    companion object {
        private fun convertDateToTimestamp(fecha: String?, hora: String?): Timestamp {
            val formatoFecha = SimpleDateFormat("dd/mm/yyy hh:mm a")
            val stringFecha = "$fecha $hora"
            var dateFecha: Date? = null
            var newTimestamp = Timestamp.now()
            try {
                dateFecha = formatoFecha.parse(stringFecha)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            if (dateFecha != null) {
                newTimestamp = Timestamp(dateFecha)
            }
            return newTimestamp
        }
    }
}