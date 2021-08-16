package com.wenitech.cashdaily.framework.features.caja.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wenitech.cashdaily.databinding.BottomSheetRemoveMoneyBinding

class BottomSheetRemoveMoney(
    private val listener: SheetRemoveListener
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetRemoveMoneyBinding? = null
    private val binding get() = _binding!!

    interface SheetRemoveListener {
        fun onClickBottomSheetRemoveMoney(value: Double, description: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetRemoveMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonDialogoAgregarDineroCajaAgregar.setOnClickListener {
            val value: String = binding.editTextValueRemove.text.toString()
            val description = binding.editTextDescription.text.toString()

            when {
                value.isEmpty() -> {
                    binding.textInputLayoutRemoveMoney.error = "Es necesario un valor"
                }
                description.isEmpty() -> {
                    binding.textInputLayoutRemoveDescription.error =
                        "Escriba una nota sobre esta transaccion"
                }
                else -> {
                    val valueMoney = value.toDouble()
                    listener.onClickBottomSheetRemoveMoney(valueMoney, description)
                    dismiss()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}