package com.wenitech.cashdaily.framework.features.client.customerCredit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.databinding.FragmentCustomerCreditBinding
import com.wenitech.cashdaily.framework.composable.CustomerCreditScreen
import com.wenitech.cashdaily.framework.features.client.customerCredit.viewModel.CustomerCreditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerCreditFragment : Fragment() {

    private val args by navArgs<CustomerCreditFragmentArgs>()
    private val viewModel by viewModels<CustomerCreditViewModel>()
    private lateinit var navController: NavController
    private lateinit var _binding: FragmentCustomerCreditBinding
    private val binding get() = _binding

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerCreditBinding.inflate(inflater, container, false)
        return binding.root.apply {
            findViewById<ComposeView>(R.id.composeViewCustomerCredit).setContent {
                val idClient = args.idClient
                val refCredit = args.refCredit
                viewModel.setArgs(idClient, refCredit)

                CustomerCreditScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

}