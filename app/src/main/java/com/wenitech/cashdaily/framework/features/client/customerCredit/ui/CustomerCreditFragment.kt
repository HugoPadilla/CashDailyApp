package com.wenitech.cashdaily.framework.features.client.customerCredit.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.databinding.FragmentCustomerCreditBinding
import com.wenitech.cashdaily.framework.features.client.customerCredit.CustomerContract
import com.wenitech.cashdaily.framework.features.client.customerCredit.CustomerContract.LoadState.*
import com.wenitech.cashdaily.framework.features.client.customerCredit.ui.adapter.RecyclerViewCuotaAdapter
import com.wenitech.cashdaily.framework.features.client.customerCredit.viewModel.CustomerCreditViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CustomerCreditFragment : Fragment() {

    private val args by navArgs<CustomerCreditFragmentArgs>()
    private val viewModel by viewModels<CustomerCreditViewModel>()

    private lateinit var adapter: RecyclerViewCuotaAdapter
    private lateinit var navController: NavController
    private lateinit var _binding: FragmentCustomerCreditBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerCreditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.customerCreditViewModel = viewModel
        navController = Navigation.findNavController(view)

        onClickListener()
    }

    @SuppressLint("SetTextI18n")
    private fun observerViewModel() {

        viewModel.clientParcelable.observe(viewLifecycleOwner, { client ->
            binding.include.textViewNombreCliente.text = client.fullName
            binding.include.textViewDireccionCliente.text = "${client.city}, ${client.direction}"

            val drawable = if (client.isCreditActive)
                R.drawable.bg_button_dialogo_verde_solido_radiun
            else
                R.drawable.bg_button_gris_solido_radius

            binding.include.imageViewIsCreditActive.setImageResource(drawable)
        })


        viewModel.state.onEach { state ->
            when (state.loadState) {
                LOADED -> {
                    showProgressBar(false)
                    showViewCreditActive(true)
                    showViewNewCredit(false)
                    binding.textViewCreditDisponible.text = state.credit.creditDebt.toString()
                }
                IDLE -> {
                    showProgressBar(false)
                    showViewCreditActive(false)
                    showViewNewCredit(true)
                }
                LOADING -> {
                    showProgressBar(true)
                    showViewCreditActive(false)
                    showViewNewCredit(false)
                }

                ERROR -> {
                    Toast.makeText(
                        requireContext(), state.errorMessage, Toast.LENGTH_SHORT
                    ).show()
                    showProgressBar(false)
                    showViewCreditActive(false)
                    showViewNewCredit(true)
                }
            }

        }.launchIn(lifecycleScope)

        viewModel.quotaCustomer.observe(viewLifecycleOwner, {
            when (it) {
                CustomerContract.QuotaCustomerState.Empty -> {
                    showToast("List quota empty...")
                }
                CustomerContract.QuotaCustomerState.Error -> {
                    showToast("Error loading quotas")
                }
                is CustomerContract.QuotaCustomerState.Success -> {
                    adapter.setData(it.listQuota)
                }
                CustomerContract.QuotaCustomerState.Loading -> {
                    showToast("Loading quota...")
                }
            }
        })

        viewModel.resultNewQuota.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), result.msg, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Agregando cuota", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Cuota agregada exitosamente: ${result.data}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    /**
     * Click Listener en la vista
     */
    private fun onClickListener() {
        binding.fabButtonCustomerCreditNewQuota.setOnClickListener {
            openDialogNewQuota()
        }

        binding.buttonNewCredit.setOnClickListener {
            val action =
                CustomerCreditFragmentDirections.actionCustomerCreditFragmentToNavigationNewCredit(
                    viewModel.clientParcelable.value!!.id
                )
            this.navController.navigate(action)
        }
    }

    /**
     * Abre un cuadro de dialogo para agregar une nueva cuota
     */
    private fun openDialogNewQuota() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialogo_nueva_cuota, null)

        val editTextNuevaCuota: TextInputEditText =
            view.findViewById(R.id.edit_text_dialogo_nueva_cuota)

        builder.setView(view)
        builder.setTitle("Agregar nueva cuota")
        builder.setPositiveButton("Guardar") { _, _ ->

            val valueQuota = editTextNuevaCuota.text.toString().trim()

            if (valueQuota.isNotEmpty()) {
                viewModel.process(
                    CustomerContract.CustomerEvent.SetNewQuota(
                        valueQuota.toDouble()
                    )
                )
            }
        }

        builder.setNegativeButton("Cancelar") { _, _ ->
            Toast.makeText(
                context,
                "Cancelado",
                Toast.LENGTH_SHORT
            ).show()
        }
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Muestra u oculta la vista del credito activo
     */
    private fun showViewCreditActive(isVisible: Boolean) {
        if (isVisible) {
            binding.textViewTitle.visibility = View.VISIBLE
            binding.textViewCreditDisponible.visibility = View.VISIBLE
            binding.groupRecentMovements.visibility = View.VISIBLE
            binding.fabButtonCustomerCreditNewQuota.isEnabled = true
        } else {
            binding.textViewTitle.visibility = View.GONE
            binding.textViewCreditDisponible.visibility = View.GONE
            binding.groupRecentMovements.visibility = View.GONE
            binding.fabButtonCustomerCreditNewQuota.isEnabled = false
        }
    }

    /**
     * Nuestra u oculta el mensjae para agregar un nuevo credito
     */
    private fun showViewNewCredit(isVisible: Boolean) {
        if (isVisible) {
            binding.groupBlankCredit.visibility = View.VISIBLE
        } else {
            binding.groupBlankCredit.visibility = View.GONE
        }
    }

    /**
     * Muestra u oculta la barra de progreso
     */
    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}