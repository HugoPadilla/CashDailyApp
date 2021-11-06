package com.wenitech.cashdaily.framework.features.client.newClient

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.databinding.FragmentNewClientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewClientFragment : Fragment() {

    private val viewModel by viewModels<NewClientViewModel>()

    private lateinit var _binding: FragmentNewClientBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewClientBinding.inflate(
            LayoutInflater.from(inflater.context),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapterAutoCompleteTextViewGender()
        binding.buttonSaveClient.setOnClickListener { initRegisterNewClient() }

        observerLiveData()
    }

    private fun observerLiveData() {

        viewModel.resourceSaveClient.observe(viewLifecycleOwner, Observer {
            when (it) {
                is com.wenitech.cashdaily.domain.common.Resource.Failure -> {
                    showProgressBar(false)
                    onError(it.msg.toString())
                }
                is com.wenitech.cashdaily.domain.common.Resource.Loading -> {
                    showProgressBar(true)
                }
                is com.wenitech.cashdaily.domain.common.Resource.Success -> {
                    showProgressBar(false)
                    onSucces(it.data.toString())
                    findNavController().navigateUp()
                }
            }
        })
    }

    private fun setupAdapterAutoCompleteTextViewGender() {
        val opcionesGenero = arrayOf("Mujer", "Hombre", "No espesificar", "Otro")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, opcionesGenero)
        binding.autoCompleteTextViewGender.setAdapter(adapter)
    }

    fun initRegisterNewClient() {
        if (isFormValid) {
            val fullName = binding.editTextFullName.text.toString().trim { it <= ' ' }
            val cedulaClient = binding.editTextId.text.toString().trim { it <= ' ' }
            val gender = binding.autoCompleteTextViewGender.text.toString().trim { it <= ' ' }
            val phoneNumber = binding.editTextPhoneNumber.text.toString().trim { it <= ' ' }
            val city = binding.editTextCity.text.toString().trim { it <= ' ' }
            val direction = binding.editTextDirection.text.toString().trim { it <= ' ' }

            // Todo:Enviar Objeto Cliente al viewModel
            viewModel.saveNewClient(fullName, cedulaClient, gender, phoneNumber, city, direction)
        }
    }

    /**
     * Check if each editText is filled correctly
     * if not insert an error message.
     *
     * @return true if ok or false if not
     */
    private val isFormValid: Boolean
        private get() {
            var valid = true
            if (TextUtils.isEmpty(binding.editTextFullName.text)) {
                binding.editTextFullName.error = "Escribe un nombre"
                valid = false
            } else if (TextUtils.isEmpty(binding.editTextId.text)) {
                binding.editTextId.error = "Identificacion del cliente"
                valid = false
            } else if (TextUtils.isEmpty(binding.autoCompleteTextViewGender.text)) {
                binding.autoCompleteTextViewGender.error = "Elige una opcion"
                valid = false
            } else if (TextUtils.isEmpty(binding.editTextPhoneNumber.text)) {
                binding.editTextPhoneNumber.error = "Escribe un numero de contacto"
                valid = false
            } else if (TextUtils.isEmpty(binding.editTextCity.text)) {
                binding.editTextCity.error = "Agrega un ciudad"
                valid = false
            } else if (TextUtils.isEmpty(binding.editTextDirection.text)) {
                binding.editTextDirection.error = "Direcion de residencia"
                valid = false
            }
            return valid
        }

    private fun showProgressBar(showProgress: Boolean) {
        if (showProgress) {
            binding.groupContent.visibility = View.INVISIBLE
            binding.groupProgresbar.visibility = View.VISIBLE
        } else {
            binding.groupContent.visibility = View.VISIBLE
            binding.groupProgresbar.visibility = View.INVISIBLE
        }
    }

    private fun onSucces(dato: String) {
        Snackbar.make(binding.root, dato, BaseTransientBottomBar.LENGTH_LONG).show()
    }

    private fun onError(dato: String) {
        Snackbar.make(binding.root, dato, BaseTransientBottomBar.LENGTH_SHORT).show()
    }
}