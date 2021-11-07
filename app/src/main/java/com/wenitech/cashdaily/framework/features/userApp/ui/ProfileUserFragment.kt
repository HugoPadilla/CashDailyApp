package com.wenitech.cashdaily.framework.features.userApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wenitech.cashdaily.databinding.FragmentPerfilBinding
import com.wenitech.cashdaily.framework.features.userApp.viewModel.ProfileUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileUserFragment : Fragment() {

    private val profileUserViewModel by viewModels<ProfileUserViewModel>()

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPerfilBinding.inflate(LayoutInflater.from(inflater.context), container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDateOfUserOnView()
    }


    private fun setupDateOfUserOnView() {
        profileUserViewModel.userModelAppLiveData.observe(viewLifecycleOwner, { resources ->
            when (resources){
                is com.wenitech.cashdaily.domain.common.Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error: ${resources.throwable.message}", Toast.LENGTH_SHORT).show()
                }
                is com.wenitech.cashdaily.domain.common.Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                }
                is com.wenitech.cashdaily.domain.common.Resource.Success -> {
                    binding.textViewFullName.text = resources.data.fullName
                    binding.textViewEmail.text = resources.data.email
                    binding.textViewTypeUserAccount.text = "Type use account"
                    binding.textViewTypeSubscription.text = "resources.data.typeSuscription"
                }
            }
        })
    }
}