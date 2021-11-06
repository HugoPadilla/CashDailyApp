package com.wenitech.cashdaily.framework.features.caja.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wenitech.cashdaily.databinding.FragmentCajaBinding
import com.wenitech.cashdaily.data.entities.CashTransactionsModel
import com.wenitech.cashdaily.framework.features.caja.BoxContract
import com.wenitech.cashdaily.framework.features.caja.ui.adapter.RecyclerViewGastoAdapter
import com.wenitech.cashdaily.framework.features.caja.viewModel.BoxViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class BoxFragment : Fragment(), RecyclerViewGastoAdapter.Listener, BottomShetDialogAddMoney.SheetAddMoneyListener, BottomSheetRemoveMoney.SheetRemoveListener {

    private val viewModel by viewModels<BoxViewModel>()
    private var _binding: FragmentCajaBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewCashMovement: RecyclerViewGastoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCajaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickViewListener()
        setupRecyclerViewMovement()
        //observeLiveData()
    }

    private fun onClickViewListener() {
        binding.cardViewCajaAgregar.setOnClickListener {
            val bottomShetDialogAddMoney = BottomShetDialogAddMoney(this)
            bottomShetDialogAddMoney.show(requireFragmentManager(), "")
        }

        binding.cardViewCajaRetirar.setOnClickListener {
            val bottomSheetRemoveMoney = BottomSheetRemoveMoney(this)
            bottomSheetRemoveMoney.show(requireFragmentManager(), "Open Remove bottom sheet")
        }
    }

    private fun setupRecyclerViewMovement() {
        recyclerViewCashMovement = RecyclerViewGastoAdapter(this)
        binding.recyclerViewCajaTransaciones.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCajaTransaciones.adapter = recyclerViewCashMovement
    }

    /*private fun observeLiveData() {

        viewModel.boxState.observe(viewLifecycleOwner, {
            when (it) {
                is BoxContract.BoxState.Error -> {
                    binding.textViewCajaTotalDisponible.text = resources.getString(R.string.default_money_zero)
                }
                BoxContract.BoxState.Loading -> {
                    binding.textViewCajaTotalDisponible.text = resources.getString(R.string.default_loading)
                }
                is BoxContract.BoxState.Success -> {
                    val formatMoney = NumberFormat.getCurrencyInstance()
                    binding.textViewCajaTotalDisponible.text = formatMoney.format(it.box.totalCash)
                }
            }
        })

        viewModel.cashMovement.observe(viewLifecycleOwner, {
            when (it) {
                is BoxContract.CashState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Hay un problema al para recuperar la iformacion",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                BoxContract.CashState.Loading -> {
                    Toast.makeText(
                        requireContext(),
                        "Cargando movimiento recientes",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is BoxContract.CashState.Success -> {
                    if (it.cashMovement.isNullOrEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "No hay movimiento recientes",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        recyclerViewCashMovement.setData(it.cashMovement)
                    }
                }
            }
        })
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickItemMovementBox(item: CashTransactionsModel, position: Int) {

    }

    override fun onClickBottomSheetAddMoney(money: Double, description: String) {
        viewModel.process(BoxContract.BoxEvent.AddMoneyClicked(money, description))
    }

    override fun onClickBottomSheetRemoveMoney(value: Double, description: String) {
        viewModel.process(BoxContract.BoxEvent.RemoveMoneyClicked(value, description))
    }
}