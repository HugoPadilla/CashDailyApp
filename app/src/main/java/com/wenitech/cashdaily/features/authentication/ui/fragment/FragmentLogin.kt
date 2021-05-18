package com.wenitech.cashdaily.features.authentication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.databinding.FragmentLoginBinding
import com.wenitech.cashdaily.features.authentication.viewModel.LoginViewModel
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.core.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentLogin : Fragment() {

    private val loginViewModel by activityViewModels<LoginViewModel>()
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        this._binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupOnClickListener()
        setupObserverViewModel()
    }

    private fun setupObserverViewModel() {
        loginViewModel!!.emailMessengerError.observe(viewLifecycleOwner, Observer { msg ->
            binding.textInputLayoutEmail.error = msg
        })

        loginViewModel!!.passwordMessageError.observe(viewLifecycleOwner, Observer { msg ->
            binding.textInputLayoutPassword.error = msg
        })

        loginViewModel!!.resulLogin.observe(viewLifecycleOwner, Observer<ResourceAuth<String?>> { (status, dato) ->
            when {
                status == Status.LOADING -> {
                    onProgress(true)
                }

                status === Status.SUCCESS -> {
                    Toast.makeText(context, dato, Toast.LENGTH_SHORT).show()
                    updateUi()
                }

                status === Status.ERROR -> {
                    Toast.makeText(context, dato, Toast.LENGTH_SHORT).show()
                    onProgress(false)
                }
            }
        })
    }

    private fun onProgress(isProgress: Boolean) = if (isProgress) {
        binding.groupView.visibility = View.GONE
        binding.groupProgressBar.visibility = View.VISIBLE
    } else {
        binding.groupView.visibility = View.VISIBLE
        binding.groupProgressBar.visibility = View.GONE
    }

    private fun setupOnClickListener() {

        binding.textviewResetPassword.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_fragmentLogin_to_fragmentRecoverPassword)
        }

        binding.textViewIniciarSesionRegistrarse.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_fragmentLogin_to_fragmentSignIn)
            loginViewModel.onCancelSingIn()
        }

        binding.editTextEmail.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (v.isFocused == hasFocus) {
                Toast.makeText(context, "Focused", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUi() {
        Navigation.findNavController(requireView()).navigate(R.id.action_fragmentLogin_to_mainNavActivity)
        requireActivity().finish()
    }
}