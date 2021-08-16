package com.wenitech.cashdaily.framework.features.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.databinding.FragmentLoginBinding
import com.wenitech.cashdaily.framework.features.authentication.login.event.LoginEvent
import com.wenitech.cashdaily.framework.features.authentication.login.uiState.LoginUiState
import com.wenitech.cashdaily.framework.features.authentication.login.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this._binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = loginViewModel

        setupObserverViewModel()
        setupOnClickListener()
    }

    private fun setupObserverViewModel() {
        loginViewModel.loginUiState.observe(viewLifecycleOwner, { UiState ->
            when (UiState) {
                is LoginUiState.Error -> {
                    Toast.makeText(context, UiState.message, Toast.LENGTH_SHORT).show()
                    onProgress(false)
                }
                is LoginUiState.Loading -> {
                    onProgress(true)
                }
                is LoginUiState.Success -> {
                    Toast.makeText(context, UiState.message, Toast.LENGTH_SHORT).show()
                    navigateToHomeFragment()
                }
                LoginUiState.Init -> {
                    onProgress(false)
                }
                is LoginUiState.EmailMessageError -> {
                    binding.textInputLayoutEmail.error = UiState.message
                }
                is LoginUiState.PasswordMessageError -> {
                    binding.textInputLayoutPassword.error = UiState.message
                }
            }
        })
    }

    private fun setupOnClickListener() {
        binding.buttonIniciarSesion.setOnClickListener {
            loginViewModel.process(
                LoginEvent.LoginClicked(
                    loginViewModel._email.value.toString().trim(),
                    loginViewModel._password.value.toString().trim()
                )
            )
        }

        binding.textviewResetPassword.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_fragmentLogin_to_fragmentRecoverPassword)
        }

        binding.textViewIniciarSesionRegistrarse.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_fragmentLogin_to_fragmentSignIn)
        }

        binding.editTextEmail.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (v.isFocused == hasFocus) {
                Toast.makeText(context, "Focused", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onProgress(isProgress: Boolean) = if (isProgress) {
        binding.groupView.visibility = View.GONE
        binding.groupProgressBar.visibility = View.VISIBLE
    } else {
        binding.groupView.visibility = View.VISIBLE
        binding.groupProgressBar.visibility = View.GONE
    }

    private fun navigateToHomeFragment() {
        Navigation.findNavController(requireView())
            .navigate(R.id.action_fragmentLogin_to_homeFragment)
    }
}