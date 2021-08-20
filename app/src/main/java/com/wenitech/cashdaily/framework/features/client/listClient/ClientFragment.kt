package com.wenitech.cashdaily.framework.features.client.listClient

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
import com.wenitech.cashdaily.NavGraphMainDirections
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.databinding.FragmentClientBinding
import com.wenitech.cashdaily.framework.composable.ClientsComposeScreen
import com.wenitech.cashdaily.framework.features.client.listClient.viewModel.ClientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientFragment : Fragment() {

    private val clientViewModel by viewModels<ClientViewModel>()
    private lateinit var binding: FragmentClientBinding
    private lateinit var navController: NavController

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientBinding.inflate(inflater, container, false)
        return binding.root.apply {
            findViewById<ComposeView>(R.id.composeViewClients).setContent {
                ClientsComposeScreen(
                    viewModel = clientViewModel,
                    onFloatingButtonClick = { navigateToNewClient() },
                    onClientClick = { idClient, refCredit ->
                        navigateToCustomerCredit(
                            idClient = idClient,
                            refCredit = refCredit
                        )
                    })
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())
    }

    private fun navigateToNewClient() {
        navController.navigate(R.id.action_clientFragment_to_newClientActivity)
    }

    private fun navigateToCustomerCredit(idClient: String, refCredit: String) {
        val action = NavGraphMainDirections.actionGlobalCustomerCreditFragment(idClient, refCredit)
        navController.navigate(action)
    }
}