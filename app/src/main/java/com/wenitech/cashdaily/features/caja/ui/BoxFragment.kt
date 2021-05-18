package com.wenitech.cashdaily.features.caja.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.model.MovimientoCaja
import com.wenitech.cashdaily.databinding.FragmentCajaBinding
import com.wenitech.cashdaily.features.caja.ui.adapter.RecyclerViewGastoAdapter
import com.wenitech.cashdaily.features.caja.viewModel.BoxViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

@AndroidEntryPoint
class BoxFragment : Fragment(), RecyclerViewGastoAdapter.Listener {

    private var _binding: FragmentCajaBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<BoxViewModel>()

    private var recyclerViewGastoAdapter: RecyclerViewGastoAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCajaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickViewLinestener()
        setupRecyclerViewMovimientos()
        observeLiveDatas()
    }

    private fun setupRecyclerViewMovimientos() {
        recyclerViewGastoAdapter = RecyclerViewGastoAdapter(this)
        binding.recyclerViewCajaTransaciones.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCajaTransaciones.adapter = recyclerViewGastoAdapter
    }

    private fun onClickViewLinestener() {
        binding.cardViewCajaAgregar.setOnClickListener { openDialogAddMoney() }
        binding.cardViewCajaRetirar.setOnClickListener { openDialogSubtractMoney() }
    }

    private fun observeLiveDatas() {

        viewModel.boxLiveData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading caja...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    val formatMoney = NumberFormat.getCurrencyInstance()
                    binding.textViewCajaTotalDisponible.text = formatMoney.format(resource.data!!.totalCaja)
                }
            }

        })

        viewModel.getRecentMovements.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error al cargar las cuotas: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading movimientos recientes...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Succes loader mvimientos", Toast.LENGTH_SHORT).show()
                    recyclerViewGastoAdapter?.setData(it.data!!)
                }
            }
        })

        viewModel.resourceAddMoneyOnBox.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Agregando dinero a la caja", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Dinero agregado exitosamente", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.resourceRemoveMoneyOnBox.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Retirando dinero de la caja", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Dinero retirado exitosamente", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun openDialogAddMoney() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialogo_agregar_dinero_caja, null)
        builder.setView(view)
        val dialog = builder.create()
        val textInputLayoutAgregar: TextInputLayout = view.findViewById(R.id.text_input_layout_dialogo_agregar_dinero_caja_valo)
        val editTextValor: TextInputEditText = view.findViewById(R.id.edit_text_dialogo_agregar_dinero_caja_valor)
        val buttonAgregar: AppCompatButton = view.findViewById(R.id.button_dialogo_agregar_dinero_caja_agregar)
        val imageViewCerrarDialogo = view.findViewById<ImageView>(R.id.image_view_dialogo_agregar_dinero_cerrar)
        imageViewCerrarDialogo.setOnClickListener { dialog.dismiss() }
        buttonAgregar.setOnClickListener {
            val valor = editTextValor.text.toString().trim { it <= ' ' }
            if (valor.isEmpty()) {
                textInputLayoutAgregar.error = "Escribe un valor"
            } else {
                buttonAgregar.isEnabled = false
                val valorAgregar = valor.toDouble()
                dialog.dismiss()
                viewModel.addMoneyOnBox(valorAgregar)
            }
        }
        dialog.show()
    }

    fun openDialogSubtractMoney() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialogo_retirar_dinero_caja, null)
        builder.setView(view)
        val dialog = builder.create()
        val textInputLayoutRetirar: TextInputLayout = view.findViewById(R.id.text_input_layout_dialogo_retirar_dinero_caja_val)
        val editTextValor: TextInputEditText = view.findViewById(R.id.edit_text_dialogo_agregar_dinero_caja_valor)
        val buttonAgregar: AppCompatButton = view.findViewById(R.id.button_dialogo_agregar_dinero_caja_agregar)
        val imageViewCerrarDialogo = view.findViewById<ImageView>(R.id.image_view_dialogo_retirar_dinero_cerrar)
        imageViewCerrarDialogo.setOnClickListener { dialog.dismiss() }
        buttonAgregar.setOnClickListener {
            val valor = editTextValor.text.toString().trim { it <= ' ' }
            if (valor.isEmpty()) {
                textInputLayoutRetirar.error = "Escribe un valor"
            } else {
                buttonAgregar.isEnabled = false
                val valorRetirar = valor.toDouble()
                dialog.dismiss()
                viewModel.removeMoney(valorRetirar)
            }
        }
        dialog.show()
    }

    override fun onClickItemMovementBox(item: MovimientoCaja, position: Int) {

    }
}