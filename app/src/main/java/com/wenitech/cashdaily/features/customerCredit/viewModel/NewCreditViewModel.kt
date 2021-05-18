package com.wenitech.cashdaily.features.customerCredit.viewModel

import android.text.TextUtils
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.data.model.Credito
import com.wenitech.cashdaily.domain.interaction.credit.SaveCreditOfClientUseCase
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewCreditViewModel @ViewModelInject constructor(
        private val saveCreditOfClientUseCase: SaveCreditOfClientUseCase,
) : ViewModel() {

    private val _idClient = MutableLiveData<String>()
    val idClient get() = _idClient

    private val _resultSaveNewCredit = MutableLiveData<ResourceAuth<String>>()
    val resultSaveNewCredit: MutableLiveData<ResourceAuth<String>> get() = _resultSaveNewCredit

    private val _resultFinishProceso = MutableLiveData<ResourceAuth<String>>()
    val resultFinishProcess get() = _resultFinishProceso


    // MutableLiveData Of NewCreditFragment for views
    var _fechaCreditMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val fechaCreditLiveData: LiveData<String>
        get() = _fechaCreditMutableLiveData

    var _hourCredit: MutableLiveData<String> = MutableLiveData()
    val hourCredit: LiveData<String>
        get() = _hourCredit

    var _valueCredit: MutableLiveData<String> = MutableLiveData()
    val valueCreditLiveData: LiveData<String>
        get() = _valueCredit

    var _porcentajeCredit: MutableLiveData<String> = MutableLiveData()
    val porcentajeCreditLiveData: LiveData<String>
        get() = _porcentajeCredit

    var _totalCredit: MutableLiveData<String> = MutableLiveData()
    val totalCreditLiveData: LiveData<String>
        get() = _totalCredit

    var _plazoCredit: MutableLiveData<String> = MutableLiveData()
    val plazoCreditLiveData: LiveData<String>
        get() = _plazoCredit

    var _valueCuotaCredit: MutableLiveData<String> = MutableLiveData()
    val valueCuotaCreditLiveData: LiveData<String>
        get() = _valueCuotaCredit

    var _formaCobroCredit: MutableLiveData<String> = MutableLiveData()
    val formaCobroCreditLiveData: LiveData<String>
        get() = _formaCobroCredit

    var _radioGrupSelecte: MutableLiveData<Int> = MutableLiveData(View.GONE)


    // MutableLiveData messageErro views
    var _fechaCreditMessageError = MutableLiveData<String>()
    val fechaCreditMessageError: LiveData<String>
        get() = _fechaCreditMessageError

    var _hourCreditMessageError = MutableLiveData<String>()
    val hourCreditMessageError: LiveData<String>
        get() = _hourCreditMessageError

    var _valueCreditMessageError = MutableLiveData<String>()
    val valueCreditMessageError: LiveData<String>
        get() = _valueCreditMessageError

    var _porcentajeCreditMessageError = MutableLiveData<String>()
    val porcentajeCreditMessageError: LiveData<String>
        get() = _porcentajeCreditMessageError

    var _totalCreditMessageError = MutableLiveData<String>()
    val totalCreditMessageError: LiveData<String>
        get() = _totalCreditMessageError

    var _plazoCreditMessageError = MutableLiveData<String>()
    val plazoCreditMessageError: LiveData<String>
        get() = _plazoCreditMessageError

    var _valueCuotaCreditMessageError = MutableLiveData<String>()
    val valueCuotaCreditMessageError: LiveData<String>
        get() = _valueCuotaCreditMessageError


    /**
     * Llama al usecase para guardar un nuevo credito
     */
    fun saveCustomerCreditFromClient() {

        if (isFormCreditValid && isFormModalityCreditValid) {
            val newCredit = buildCreditObjet()

            saveCreditOfClientUseCase.execute(_idClient.value.toString(), newCredit).observeForever {
                _resultSaveNewCredit.value = it
            }
        }

    }


    fun changerArgumentSafeArg(idClientString: String) {
        _idClient.value = idClientString
    }

    // Metodo util
    private fun buildCreditObjet(): Credito {

        val newCredit = Credito()

        _resultSaveNewCredit.value = ResourceAuth.loading("Agregando credito")

        newCredit.fechaPretamo = convertDateToTimestamp(_fechaCreditMutableLiveData.value, _hourCredit.value)
        newCredit.valorPrestamo = _valueCredit.value!!.trim { it <= ' ' }.toDouble()
        newCredit.porcentaje = _porcentajeCredit.value!!.trim { it <= ' ' }.toDouble()
        newCredit.totalPrestamo = _totalCredit.value!!.trim { it <= ' ' }.toDouble()
        newCredit.deudaPrestamo = _totalCredit.value!!.trim { it <= ' ' }.toDouble()

        when (_radioGrupSelecte.value) {
            R.id.radio_button_nuevo_credito_diario -> {
                newCredit.modalida = "Diario"
                newCredit.numeroCuotas = _plazoCredit.value!!.trim { it <= ' ' }.toInt()
                newCredit.valorCuota = _valueCuotaCredit.value!!.trim { it <= ' ' }.toDouble()

                // Modificar en funcion del selecionado por el usuario
                newCredit.isNoCobrarSabados = false
                newCredit.isNoCobrarDomingos = false

                // Generar la proxima fecha de cobro cuado es diarios
                val dateFecha = Date()
                val calendar = Calendar.getInstance()
                calendar.time = dateFecha
                calendar.add(Calendar.DAY_OF_YEAR, 1)
                newCredit.fechaProximaCuota = Timestamp(calendar.time)
            }
            R.id.radio_button_nuevo_credito_semanal -> {
                newCredit.modalida = "Semanal"
                newCredit.numeroCuotas = _plazoCredit.value!!.trim { it <= ' ' }.toInt()
                newCredit.valorCuota = _valueCuotaCredit.value!!.trim { it <= ' ' }.toDouble()
                newCredit.isNoCobrarSabados = false
                newCredit.isNoCobrarDomingos = false
            }
            R.id.radio_button_nuevo_credito_quincenal -> {
                newCredit.modalida = "Quincenal"
                newCredit.numeroCuotas = _plazoCredit.value!!.trim { it <= ' ' }.toInt()
                newCredit.valorCuota = _valueCuotaCredit.value!!.trim { it <= ' ' }.toDouble()
                newCredit.isNoCobrarSabados = false
                newCredit.isNoCobrarDomingos = false
            }
            R.id.radio_button_nuevo_credito_mensual -> {
                newCredit.modalida = "Mensual"
                newCredit.numeroCuotas = _plazoCredit.value!!.trim { it <= ' ' }.toInt()
                newCredit.valorCuota = _valueCuotaCredit.value!!.trim { it <= ' ' }.toDouble()
                newCredit.isNoCobrarSabados = false
                newCredit.isNoCobrarDomingos = false
            }
        }


        return newCredit
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


    fun initDateForEditText() {
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
        _resultSaveNewCredit.value = ResourceAuth.init()
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