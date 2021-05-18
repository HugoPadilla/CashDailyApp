package com.wenitech.cashdaily.features.customerCredit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.firebase.MyFirebaseAuth
import com.wenitech.cashdaily.data.model.Cuota
import com.wenitech.cashdaily.databinding.FragmentCustomerCreditBinding
import com.wenitech.cashdaily.features.customerCredit.viewModel.CustomerCreditViewModel
import com.wenitech.cashdaily.features.customerCredit.ui.adapter.RecyclerViewCuotaAdapter
import com.wenitech.cashdaily.features.customerCredit.viewModel.ShareCustomerCreditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerCreditFragment : Fragment(), RecyclerViewCuotaAdapter.RecyclerViewCuotaListener {

    private val args by navArgs<CustomerCreditFragmentArgs>()
    private val shareCustomerCreditViewModel by activityViewModels<ShareCustomerCreditViewModel>()
    private val viewModel by viewModels<CustomerCreditViewModel>();

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
        binding.customerCreditViewModel = viewModel
        binding.lifecycleOwner = this
        navController = Navigation.findNavController(view)

        getArgumentFromSafeArg()
        setupRecyclerView()
        observerViewModel()
        OnClickListener()
    }

    private fun setupRecyclerView() {
        adapter = RecyclerViewCuotaAdapter(this)
        binding.recyclerViewCustomerCreditQuota.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCustomerCreditQuota.adapter = adapter
    }

    /**
     * Obtiene el idClient, argumento enviado por ClientFramgnet
     * y se pasa a un MutableLiveData en ViewModel para compartido
     */
    private fun getArgumentFromSafeArg() {
        viewModel.changeArgument(args.clientObject)
        viewModel.setDocRefCreditClient(args.clientObject.documentReferenceCreditActive)
    }

    private fun observerViewModel() {

        shareCustomerCreditViewModel.referenciasNewCredit.observe(viewLifecycleOwner, Observer { newReferencia ->
            viewModel.setDocRefCreditClient(newReferencia)
        })

        // Observa la entidad cliente para mostrarlos la informacion del cliente
        viewModel.clientParcelable.observe(viewLifecycleOwner, Observer {
            binding.include.textViewNombreCliente.text = it.fullName
            binding.include.textViewDireccionCliente.text = "${it.city}, ${it.direction}"
            if (it.isCreditActive) {
                binding.include.imageViewIsCreditActive.setImageResource(R.drawable.bg_button_dialogo_verde_solido_radiun)
            } else {
                binding.include.imageViewIsCreditActive.setImageResource(R.drawable.bg_button_gris_solido_radius)
            }
        })

        viewModel.docCreditActive.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()){
                viewModel.getCreditClient("")
                viewModel.getCuotasCreditClient("")
            } else {
                viewModel.getCreditClient(it)
                viewModel.getCuotasCreditClient(it)
            }
        })

        // Observa si hay credito activo y muestra la ui corespondiente
        viewModel.creditClientLiveData.observe(viewLifecycleOwner, Observer { credit ->
            when (credit) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading credit...", Toast.LENGTH_SHORT).show()
                    showProgressBar(true)
                    showViewCreditActive(false)
                    showViewNewCredit(false)
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Credit succes: ${credit.data!!.totalPrestamo}", Toast.LENGTH_SHORT).show()
                    showProgressBar(false)
                    if (credit.data.totalPrestamo != null){
                        showViewCreditActive(true)
                        showViewNewCredit(false)
                        binding.textViewCreditDisponible.text = credit.data.deudaPrestamo.toString()
                    } else {
                        showViewCreditActive(false)
                        showViewNewCredit(true)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error credito: ${credit.message}", Toast.LENGTH_SHORT).show()
                    showProgressBar(false)
                    showViewCreditActive(false)
                    showViewNewCredit(true)
                }
            }
        })

        viewModel.listQuotaCreditLiveData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading cuotas...", Toast.LENGTH_LONG).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Succes cuotas: ${resource.data!!.size}", Toast.LENGTH_SHORT).show()
                    adapter.setData(resource.data)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error cuota : ${resource.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.resultNewQuota.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Agregando cuota", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Cuota agregada exitosamente: ${it.data}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    // Listener
    private fun OnClickListener() {
        _binding.fabButtonCustomerCreditNewQuota.setOnClickListener {
            openDialogNewQuota()
        }

        _binding.buttonNewCredit.setOnClickListener {
            val action = CustomerCreditFragmentDirections.actionCustomerCreditFragmentToNavigationNewCredit(
                    viewModel.clientParcelable.value!!.id
            )
            this.navController.navigate(action)
        }
    }

    /**
     * Abre dialogo para agregar une nueva cuota
     */
    private fun openDialogNewQuota() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialogo_nueva_cuota, null)
        val editTextNuevaCuota: TextInputEditText = view.findViewById(R.id.edit_text_dialogo_nueva_cuota)
        builder.setView(view)
        builder.setTitle("Agregar nueva cuota")
        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevaCuota = editTextNuevaCuota.text.toString().trim { it <= ' ' }
            if (nuevaCuota.isEmpty()) {
                // On error form
            } else {
                val newQuota = editTextNuevaCuota.text.toString().trim { it <= ' ' }.toDouble()
                viewModel.setNewQuota(newQuota, viewModel.clientParcelable.value!!.id, viewModel.docCreditActive.value, MyFirebaseAuth.getInstance().user)
            }
        }

        builder.setNegativeButton("Cancelar") { _, _ -> Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show() }
        val dialog = builder.create()
        dialog.show()
    }

    fun showViewCreditActive(isVisible: Boolean) {
        if (isVisible) {
            binding.groupContentCustomer.visibility = View.VISIBLE
            binding.textView.visibility = View.VISIBLE
            binding.textViewCreditDisponible.visibility = View.VISIBLE
            binding.fabButtonCustomerCreditNewQuota.isEnabled = true
        } else {
            binding.groupContentCustomer.visibility = View.GONE
            binding.textView.visibility = View.GONE
            binding.textViewCreditDisponible.visibility = View.GONE
            binding.fabButtonCustomerCreditNewQuota.isEnabled = false
        }
    }

    fun showViewNewCredit(isVisible: Boolean) {
        if (isVisible) {
            binding.groupBlankCredit.visibility = View.VISIBLE
        } else {
            binding.groupBlankCredit.visibility = View.GONE
        }
    }

    fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    override fun listenerOfItem(item: Cuota, position: Int, root: CardView) {
        // Todo : cuota on Click
    }
}