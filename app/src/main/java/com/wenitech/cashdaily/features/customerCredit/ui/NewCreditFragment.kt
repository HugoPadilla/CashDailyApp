package com.wenitech.cashdaily.features.customerCredit.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.core.Dialog.DatePickerFragment
import com.wenitech.cashdaily.databinding.FragmentNewCreditBinding
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.core.Status
import com.wenitech.cashdaily.features.customerCredit.viewModel.CustomerCreditViewModel
import com.wenitech.cashdaily.features.customerCredit.viewModel.NewCreditViewModel
import com.wenitech.cashdaily.features.customerCredit.viewModel.ShareCustomerCreditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewCreditFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private val shareCustomerCreditViewModel by activityViewModels<ShareCustomerCreditViewModel>()
    private val viewModel by viewModels<NewCreditViewModel>()

    private lateinit var datePickerFragment: DatePickerFragment
    private lateinit var navController: NavController

    private var _binding: FragmentNewCreditBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<NewCreditFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNewCreditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customerCreditViewMode = this.viewModel
        binding.lifecycleOwner = this
        navController = Navigation.findNavController(requireView())

        datePickerFragment = DatePickerFragment.newInstance(this)

        viewModel.initDateForEditText()
        viewModel.changerArgumentSafeArg(args.idClient) //Envia el idClient al viewModel
        initAutoCompletTexViewPorcentaje()
        addListenerRadioGroup()
        initClickListenerViews()
        setupObserverViewModel()
    }

    private fun initAutoCompletTexViewPorcentaje() {
        val listOptionMenuDropdown = arrayOf("00", "03", "05", "10", "15", "20", "30")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, listOptionMenuDropdown)
        binding.autoCompleteTextNuevoCreditoPorcentaje.setAdapter(adapter)
    }

    private fun addListenerRadioGroup() {
        binding.radioGroupNuevoCreditoModalidad.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radio_button_nuevo_credito_diario) {
                if (viewModel.isFormCreditValid) {
                    viewModel._radioGrupSelecte.setValue(checkedId)
                } else {
                    binding.radioGroupNuevoCreditoModalidad.clearCheck()
                    return@OnCheckedChangeListener
                }
            } else if (checkedId == R.id.radio_button_nuevo_credito_semanal) {
                if (viewModel.isFormCreditValid) {
                    viewModel._radioGrupSelecte.setValue(checkedId)
                } else {
                    binding.radioGroupNuevoCreditoModalidad.clearCheck()
                    return@OnCheckedChangeListener
                }
            } else if (checkedId == R.id.radio_button_nuevo_credito_quincenal) {
                if (this.viewModel.isFormCreditValid) {
                    viewModel._radioGrupSelecte.setValue(checkedId)
                } else {
                    binding.radioGroupNuevoCreditoModalidad.clearCheck()
                    return@OnCheckedChangeListener
                }
            } else if (checkedId == R.id.radio_button_nuevo_credito_mensual) {
                if (viewModel.isFormCreditValid) {
                    viewModel._radioGrupSelecte.setValue(checkedId)
                } else {
                    binding.radioGroupNuevoCreditoModalidad.clearCheck()
                    return@OnCheckedChangeListener
                }
            }
            if (viewModel.isFormCreditValid) {
                viewModel._radioGrupSelecte.setValue(checkedId)
            } else {
                viewModel._radioGrupSelecte.setValue(View.GONE)
            }
        })
    }

    private fun initClickListenerViews() {

        binding.editTextNuevoCreditoFecha.setOnClickListener {
            datePickerFragment.show(requireFragmentManager(), "Seleciona la fecha")
        }

        binding.editTextNuevoCreditoValorPrestamo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                autoCompletValorTotal()
            }
        })
        binding.autoCompleteTextNuevoCreditoPorcentaje.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                autoCompletValorTotal()
            }
        })
        binding.editTextNuevoCreditoPlazo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                autoCompletValorQuota()
            }
        })
    }

    private fun autoCompletValorTotal() {
        if (!TextUtils.isEmpty(binding.autoCompleteTextNuevoCreditoPorcentaje.text)
                && !TextUtils.isEmpty(binding.editTextNuevoCreditoValorPrestamo.text)) {
            val valorPrestamo = java.lang.Double.valueOf(viewModel._valueCredit.value.toString())
            val valorPorcentaje = java.lang.Double.valueOf(binding.autoCompleteTextNuevoCreditoPorcentaje.text.toString())
            val totalPrestamo = valorPrestamo + valorPrestamo * valorPorcentaje / 100
            viewModel._totalCredit.setValue(totalPrestamo.toString())
        } else {
            viewModel._totalCredit.setValue("0")
        }
    }

    private fun autoCompletValorQuota() {
        if (!TextUtils.isEmpty(binding.editTextNuevoCreditoPlazo.text)) {
            val totalPrestamo = java.lang.Double.valueOf(viewModel._totalCredit.value!!)
            val plazoPrestamo = java.lang.Double.valueOf(viewModel._plazoCredit.value!!)
            val valorCuota = totalPrestamo / plazoPrestamo
            viewModel._valueCuotaCredit.setValue(valorCuota.toString())
        } else {
            viewModel._valueCuotaCredit.setValue("0")
        }
    }

    private fun setupObserverViewModel() {

        // Observer TextInputLayout
        viewModel._fechaCreditMessageError.observe(viewLifecycleOwner, { s -> binding.textInputLayoutNuevoCreditoFecha.error = s })
        viewModel._hourCreditMessageError.observe(viewLifecycleOwner, { s -> binding.textInputLayoutNuevoCreditoHora.error = s })
        viewModel._valueCreditMessageError.observe(viewLifecycleOwner, { s -> binding.textInputLayoutNuevoCreditoValorPrestamo.error = s })
        viewModel._porcentajeCreditMessageError.observe(viewLifecycleOwner, { s -> binding.textInputLayoutNuevoCreditoPorcentaje.error = s })
        viewModel._totalCreditMessageError.observe(viewLifecycleOwner, { s -> binding.textInputLayoutNuevoCreditoTotalCredito.error = s })
        viewModel._plazoCreditMessageError.observe(viewLifecycleOwner, { s -> binding.textInputLayoutNuevoCreditoPlazo.error = s })
        viewModel._valueCuotaCreditMessageError.observe(viewLifecycleOwner, { s -> binding.textInputLayoutNuevoCreditoValorCuota.error = s })

        // Observer RadiGroup selecionado
        viewModel._radioGrupSelecte.observe(viewLifecycleOwner, Observer { idResource ->
            when (idResource) {
                View.GONE -> changeFormForModalityCredit(View.GONE, "", View.GONE)
                R.id.radio_button_nuevo_credito_diario -> changeFormForModalityCredit(View.VISIBLE, "Dias de plazo", View.VISIBLE)
                R.id.radio_button_nuevo_credito_semanal -> changeFormForModalityCredit(View.VISIBLE, "Semanas de plazo", View.GONE)
                R.id.radio_button_nuevo_credito_quincenal -> changeFormForModalityCredit(View.VISIBLE, "Quincenas de plazo", View.GONE)
                R.id.radio_button_nuevo_credito_mensual -> changeFormForModalityCredit(View.VISIBLE, "Mese de plazo", View.GONE)
            }
        })

        // Resultado de guardar un nuevo credito
        viewModel.resultSaveNewCredit.observe(viewLifecycleOwner, Observer<ResourceAuth<String?>> { (status, dato) ->
            when (status) {
                Status.INIT -> showProgressIndicator(false)
                Status.LOADING -> showProgressIndicator(true)
                Status.SUCCESS -> {
                    onSuccess()
                    viewModel.resertForm()

                    /*customerCreditViewModel.getCreditClientByDocumentReference(dato!!)
                    customerCreditViewModel._documentReferenceCreditClient.value = dato
                    customerCreditViewModel._resultNewCredit.value = Resource.success(dato)
                    customerCreditViewModel._resultNewQuota.value = Resource.init()*/
                    if (dato != null) {
                        shareCustomerCreditViewModel.setReferenciaCredit(dato)
                    }
                    navController.navigateUp()
                }
                Status.FAILED -> {
                    showProgressIndicator(false)
                    onError()
                }
                Status.COLLICION -> TODO()
                Status.ERROR -> TODO()
            }
        })
    }

    // Method Util
    private fun changeFormForModalityCredit(visibilitiPlazo: Int, visibilitiPlazoTextHint: String, visibilitiFinSemana: Int) {
        binding.textInputLayoutNuevoCreditoPlazo.visibility = visibilitiPlazo
        binding.textInputLayoutNuevoCreditoValorCuota.visibility = visibilitiPlazo
        binding.textInputLayoutNuevoCreditoPlazo.hint = visibilitiPlazoTextHint
        binding.textViewNoCobrarDias.visibility = visibilitiFinSemana
        binding.switchNuevoCreditoSabados.visibility = visibilitiFinSemana
        binding.switchNuevoCreditoDomingo.visibility = visibilitiFinSemana
    }

    private fun showProgressIndicator(showProgress: Boolean) {
        when (showProgress) {
            true -> {
                binding.constrainLayoutFormulario.visibility = View.GONE
                binding.progressBarNuevoCredito.visibility = View.VISIBLE
            }
            false -> {
                binding.constrainLayoutFormulario.visibility = View.VISIBLE
                binding.progressBarNuevoCredito.visibility = View.GONE
            }
        }
    }

    private fun onSuccess() {
        Toast.makeText(context, "Credito agregado", Toast.LENGTH_SHORT).show()
    }

    private fun onError() {
        Toast.makeText(context, "No es posible agregar el credito", Toast.LENGTH_SHORT).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Toast.makeText(requireContext(), "$month", Toast.LENGTH_SHORT).show()
    }
}