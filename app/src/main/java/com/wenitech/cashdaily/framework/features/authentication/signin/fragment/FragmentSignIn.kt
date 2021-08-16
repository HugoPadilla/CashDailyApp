package com.wenitech.cashdaily.framework.features.authentication.signin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.databinding.FragmentSignInBinding
import com.wenitech.cashdaily.framework.features.authentication.signin.event.SignInEvent
import com.wenitech.cashdaily.framework.features.authentication.signin.uiState.SignInUiState
import com.wenitech.cashdaily.framework.features.authentication.signin.viewModel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSignIn : Fragment() {
    private val viewModel by viewModels<SignInViewModel>()

    private lateinit var _binding: FragmentSignInBinding
    private val binding get() = _binding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        this._binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        navController = Navigation.findNavController(binding.root)

        setupClickListener()
        setupObserver()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //viewModel.onCancelSingIn()
                    navController.navigate(R.id.action_fragmentSignIn_to_fragmentLogin)
                }
            })
    }

    private fun setupClickListener() {
        binding.buttonSingUp.setOnClickListener {
            viewModel.process(
                SignInEvent.SignInClicked(
                    email = viewModel._email.value.toString().trim(),
                    password = viewModel._password.value.toString().trim(),
                    passwordConfirm = viewModel._passwordConfirm.value.toString().trim()
                )
            )
        }
    }

    private fun setupObserver() {
        viewModel.signInUiState.observe(viewLifecycleOwner, {
            when (it) {
                is SignInUiState.Collicion -> {
                    onProgress(false)
                    DialogInfoCollision()
                }
                is SignInUiState.Error -> {
                    DialogInfoFailed(it.msg)
                    onProgress(false)
                }
                SignInUiState.Loading -> onProgress(true)
                SignInUiState.Success -> navigateToHomeFragment()
                is SignInUiState.EmailMessageError -> {
                    binding.textInputLayoutEmail.error = it.msg
                }
                is SignInUiState.PasswordMessageError -> {
                    binding.textInputLayoutPassword.error = it.msg
                }
                is SignInUiState.PasswordConfirmMessageError -> {
                    binding.textInputLayoutConfirmPassword.error = it.msg
                }
            }
        })
    }

    private fun DialogInfoCollision() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Ya existente una cuenta con este correo")
            .setMessage("Intenta iniciar sesion con tu credenciales o recupera tu contraseña")
            .setPositiveButton("Iniciar sesion") { dialog, which ->
                navController.navigate(R.id.action_fragmentSignIn_to_fragmentLogin)
            }
            .setNegativeButton("Volver") { dialog, which ->

            }
            .show()
    }

    private fun DialogInfoFailed(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun onProgress(isProgress: Boolean) {
        if (isProgress) {
            binding.groupView.visibility = View.GONE
            binding.groupProgress.visibility = View.VISIBLE
        } else {
            binding.groupView.visibility = View.VISIBLE
            binding.groupProgress.visibility = View.GONE
        }
    }

    private fun navigateToHomeFragment() {
        navController.navigate(R.id.action_fragmentSignIn_to_homeFragment)
    }
}