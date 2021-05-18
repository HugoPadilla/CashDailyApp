package com.wenitech.cashdaily.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.databinding.HomeFragmentBinding
import com.wenitech.cashdaily.features.home.viewModel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeFragmentViewModel by viewModels<HomeFragmentViewModel>()

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

    private lateinit var navController: NavController


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireView())
        if (FirebaseAuth.getInstance().currentUser != null) {
            userBox
        }

        if (firebaseAuth.currentUser != null) {
            observerLiveData()
        }

        onClickListner()

    }

    private fun onClickListner() {

        binding.cardViewCobrarHoy.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_cobrarHoyFragment)
        }

        binding.cardViewCobrarAtrasados.setOnClickListener {

        }

        binding.cardViewCobrarVencidos.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_vencidosFragment)
        }

    }

    /**
     * Llama al metodo getBox de ViewModel
     * Luego observa el Objeto cajaLiveData expuesto por el viewModel y actualiza la UI
     */
    private val userBox: Unit
        private get() {

            homeFragmentViewModel.cajaLiveData.observe(viewLifecycleOwner, { resource ->
                when (resource) {
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Loading caja...", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        val formatMoney = NumberFormat.getCurrencyInstance()
                        binding.textViewCajaDisponible.text = formatMoney.format(resource.data!!.totalCaja)
                    }
                }
            })
        }

    private fun observerLiveData() {
        homeFragmentViewModel.profileUser.observe(viewLifecycleOwner, Observer {
            binding.textViewFirstName.text = it.nameBussine
        })
    }
}