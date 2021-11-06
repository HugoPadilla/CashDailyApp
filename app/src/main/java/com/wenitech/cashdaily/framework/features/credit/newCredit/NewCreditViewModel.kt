package com.wenitech.cashdaily.framework.features.credit.newCredit

import android.text.TextUtils
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.data.entities.CreditModel
import com.wenitech.cashdaily.data.entities.toDomain
import com.wenitech.cashdaily.domain.constant.PaymentMethodEnum
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.usecases.credit.SaveNewCreditUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewCreditViewModel @ViewModelInject constructor(
    private val auth: FirebaseAuth,
    private val saveNewCreditUseCase: SaveNewCreditUseCase,
) : ViewModel() {

    private val _idClient = MutableLiveData<String>()
    val idClient get() = _idClient

    private val _resultSaveNewCredit =
        MutableLiveData<com.wenitech.cashdaily.domain.common.Resource<String>>()
    val resultSaveNewCredit: MutableLiveData<com.wenitech.cashdaily.domain.common.Resource<String>> get() = _resultSaveNewCredit

    // MutableLiveData Of NewCreditFragment for views
    var _fechaCreditMutableLiveData: MutableLiveData<String> = MutableLiveData()
    var _hourCredit: MutableLiveData<String> = MutableLiveData()
    var _valueCredit: MutableLiveData<String> = MutableLiveData()
    var _porcentajeCredit: MutableLiveData<String> = MutableLiveData()
    var _totalCredit: MutableLiveData<String> = MutableLiveData()
    var _plazoCredit: MutableLiveData<String> = MutableLiveData()
    var _valueCuotaCredit: MutableLiveData<String> = MutableLiveData()
    var _formaCobroCredit: MutableLiveData<String> = MutableLiveData()
    var _radioGrupSelecte: MutableLiveData<Int> = MutableLiveData(View.GONE)

    // MutableLiveData messageErro views
    var _fechaCreditMessageError = MutableLiveData<String>()
    var _hourCreditMessageError = MutableLiveData<String>()
    var _valueCreditMessageError = MutableLiveData<String>()
    var _porcentajeCreditMessageError = MutableLiveData<String>()
    var _totalCreditMessageError = MutableLiveData<String>()
    var _plazoCreditMessageError = MutableLiveData<String>()
    var _valueCuotaCreditMessageError = MutableLiveData<String>()

    init {
        initDateForEditText()
    }

    /**
     * Llama al usecase para guardar un nuevo credito
     */
    fun saveCustomerCreditFromClient() {

        val uid = auth.currentUser?.uid
        if (!uid.isNullOrEmpty()) {
            if (isFormCreditValid && isFormModalityCreditValid) {
                val newCredit = buildCreditObjet()
                viewModelScope.launch {
                    saveNewCreditUseCase(uid, _idClient.value.toString(), newCredit).collect {
                        _resultSaveNewCredit.value = it
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

        val newCredit = CreditModel()

        _resultSaveNewCredit.value = com.wenitech.cashdaily.domain.common.Resource.Loading()

        newCredit.timestampCreation =
            convertDateToTimestamp(_fechaCreditMutableLiveData.value, _hourCredit.value)
        newCredit.creditValue = _valueCredit.value!!.trim {
            it <= ' '
        }.toDouble()
        newCredit.percentage = _porcentajeCredit.value!!.trim { it <= ' ' }.toInt()
        newCredit.creditTotal = _totalCredit.value!!.trim { it <= ' ' }.toDouble()
        newCredit.creditDebt = _totalCredit.value!!.trim { it <= ' ' }.toDouble()

        when (_radioGrupSelecte.value) {
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
        }


        return newCredit.toDomain()
    }


    val isFormCreditValid: Boolean
        get() {
            when {
                TextUtils.isEmpty(_fechaCreditMutableLiveData.value) -> {
                    _fechaCreditMessageError.value = "Seleciona una fecha"
                    return false
                }
                TextUtils.isEmpty(_hourCredit.value) -> {
                    _hourCreditMessageError.value = "Establece una hora"
                    return false
                }
                TextUtils.isEmpty(_valueCredit.value) -> {
                    _valueCreditMessageError.value = "Escribe un valor"
                    return false
                }
                TextUtils.isEmpty(_porcentajeCredit.value) -> {
                    _porcentajeCreditMessageError.value = "Establece un porcentaje"
                    return false
                }
                TextUtils.isEmpty(_totalCredit.value) -> {
                    _totalCreditMessageError.value = "Total de credito"
                    return false
                }
                else -> {
                    _fechaCreditMessageError.value = ""
                    _hourCreditMessageError.value = ""
                    _valueCreditMessageError.value = ""
                    _porcentajeCreditMessageError.value = ""
                    _totalCreditMessageError.setValue("")
                    return true
                }
            }
        }

    /**
     * @return
     */
    val isFormModalityCreditValid: Boolean
        get() {
            var isValid = true
            if (TextUtils.isEmpty(_plazoCredit.value)) {
                _plazoCreditMessageError.value = "Estable una plazo"
                isValid = false
            } else if (TextUtils.isEmpty(_valueCuotaCredit.value)) {
                _valueCuotaCreditMessageError.value = "Valor cuota"
                isValid = false
            }
            return isValid
        }


    private fun initDateForEditText() {
        val date = Date()
        val formatFecha = SimpleDateFormat("dd/mm/yyy")
        val formatHora = SimpleDateFormat("hh:mm a")
        val fecha = formatFecha.format(date)
        val hora = formatHora.format(date)
        _fechaCreditMutableLiveData.value = fecha
        _hourCredit.value = hora
    }

    fun resertForm() {
        _valueCredit.value = ""
        _porcentajeCredit.value = ""
        _valueCuotaCredit.value = ""
        _totalCredit.value = ""
        _plazoCredit.value = ""
        _valueCuotaCredit.value = ""
        _resultSaveNewCredit.value = com.wenitech.cashdaily.domain.common.Resource.Loading()
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