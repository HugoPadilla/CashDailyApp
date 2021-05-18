package com.wenitech.cashdaily.features.client.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wenitech.cashdaily.NavGraphMainDirections
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.model.Cliente
import com.wenitech.cashdaily.databinding.FragmentClientBinding
import com.wenitech.cashdaily.features.customerCredit.model.ClientParcelable
import com.wenitech.cashdaily.features.client.ui.adapter.RecyclerViewClientsAdapter
import com.wenitech.cashdaily.features.client.viewModel.ClientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientFragment : Fragment(), RecyclerViewClientsAdapter.MyRecyclerViewInterface {

    private val clientViewModel by viewModels<ClientViewModel>()
    private lateinit var binding: FragmentClientBinding
    private lateinit var navController: NavController
    private lateinit var mAdapter: RecyclerViewClientsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentClientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())
        setupRecyclerView()
        setupClickListener()
        setupObserver()
    }

    /**
     * Configuracion del recycler view
     */
    private fun setupRecyclerView() {
        mAdapter = RecyclerViewClientsAdapter( this)
        binding.recyclerViewTodos.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewTodos.adapter = mAdapter
        binding.recyclerViewTodos.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }
        })
    }

    /**
     * On Click Listner of views
     */
    private fun setupClickListener() {
        binding.floatingActionButton.setOnClickListener {
            navController.navigate(R.id.action_clientFragment_to_newClientActivity)
        }
    }

    /**
     * Observer
     */
    private fun setupObserver() {
        clientViewModel.getClient()
        clientViewModel.listClients.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading client...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    mAdapter.setData(it.data!!)
                }
                is Resource.Error -> {
                    Log.d("Firestore", "${it.message}")
                    Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    /**
     * Interfas listener para el item selecionado del recyclerView
     */
    override fun onItemClick(client: Cliente, position: Int, cardView: View) {
        val clientParcelable = ClientParcelable(
                client.id!!,
                client.isCreditActive,
                client.documentReferenceCreditActive?.path,
                client.fullName,
                client.cedulaNumber,
                client.gender,
                client.phoneNumber,
                client.city,
                client.direction
        )
        val action = NavGraphMainDirections.actionGlobalCustomerCreditFragment(clientParcelable)
        navController.navigate(action)
    }

    /*override fun onClientClicked(cardView: View, cliente: Cliente) {
        val clientParcelable = ClientParcelable(
                cliente.id!!,
                cliente.isCreditActive,
                cliente.documentReferenceCreditActive!!.path,
                cliente.fullName,
                cliente.cedulaNumber,
                cliente.gender,
                cliente.phoneNumber,
                cliente.city,
                cliente.direction
        )

        var action = actionGlobalCustomerCreditFragment(clientParcelable)
        navController.navigate(action)
    }*/
}