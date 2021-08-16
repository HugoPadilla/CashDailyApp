package com.wenitech.cashdaily.framework.features.caja.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wenitech.cashdaily.databinding.BottomSheetAddMoneyBinding

open class BottomShetDialogAddMoney(
    private val addMoneyListener: SheetAddMoneyListener
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAddMoneyBinding? = null
    val binding get() = _binding!!

    interface SheetAddMoneyListener {
        fun onClickBottomSheetAddMoney(money: Double, description: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetAddMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonDialogoAgregarDineroCajaAgregar.setOnClickListener {

            val value: String = binding.editTextAddMoney.text.toString()
            val description = binding.editTextDescription.text.toString()

            when {
                value.isEmpty() -> {
                    binding.textInputLayoutAddMoney.error = "Es necesario un valor"
                }
                description.isEmpty() -> {
                    binding.textInputLayoutDescription.error =
                        "Escriba una nota sobre esta transaccion"
                }
                else -> {
                    val valueMoney = value.toDouble()
                    addMoneyListener.onClickBottomSheetAddMoney(valueMoney, description)
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