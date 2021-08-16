package com.wenitech.cashdaily.framework.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.databinding.HomeFragmentBinding
import com.wenitech.cashdaily.framework.composable.HomeComposeScreen
import com.wenitech.cashdaily.framework.features.home.viewModel.HomeFragmentViewModel
import com.wenitech.cashdaily.framework.ui.theme.CashDailyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(){

    private val homeFragmentViewModel by viewModels<HomeFragmentViewModel>()
    private lateinit var _binding: HomeFragmentBinding
    private val binding get() = _binding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root.apply {
            findViewById<ComposeView>(R.id.composeView).setContent {

                CashDailyTheme {
                    HomeComposeScreen(homeFragmentViewModel)
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireView())
    }

}