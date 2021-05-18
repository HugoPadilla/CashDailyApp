package com.wenitech.cashdaily.features.authentication.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.databinding.FragmentSignInBinding
import com.wenitech.cashdaily.features.authentication.viewModel.LoginViewModel
import com.wenitech.cashdaily.core.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSignIn : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        this._binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        navController = Navigation.findNavController(binding.root)

        setupOnClickListener()
        setupObserver()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onCancelSingIn()
                navController.navigate(R.id.action_fragmentSignIn_to_fragmentLogin)
            }
        })
    }

    private fun setupOnClickListener() {

    }

    private fun setupObserver() {
        viewModel.emailMessengerError.observe(viewLifecycleOwner, Observer { mensaje ->
            binding.textInputLayoutEmail.error = mensaje
        })

        viewModel.passwordMessageError.observe(viewLifecycleOwner, Observer { mensaje ->
            binding.textInputLayoutPassword.error = mensaje
        })

        viewModel.passwordConfirmMessageError.observe(viewLifecycleOwner, Observer { mensaje ->
            binding.textInputLayoutConfirmPassword.error = mensaje
        })

        viewModel.resultSingIn.observe(viewLifecycleOwner, Observer { (status, data, msg) ->
            when (status) {
                Status.INIT -> {
                    onProgress(false)
                }
                Status.LOADING -> {
                    onProgress(true)
                }
                Status.SUCCESS -> {
                    updateUi()
                }
                Status.COLLICION -> {
                    onProgress(false)
                    DialogInfoCollision(msg.toString())
                }
                Status.FAILED -> {
                    DialogInfoFailed(msg.toString())
                    onProgress(false)
                }
                Status.ERROR -> {
                    Log.d("firebase", "Error")
                }
            }
        })
    }

    private fun DialogInfoCollision(msg: String) {
        MaterialAlertDialogBuilder(requireContext())
                .setTitle("Ya existente una cuenta con este correo")
                .setMessage("Intenta iniciar sesion con tu credenciales o recupera tu contraseña")
                .setPositiveButton("Iniciar sesion") { dialog, which ->
                    navController.navigate(R.id.action_fragmentSignIn_to_fragmentLogin)
                    viewModel.onCancelSingIn()
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

    private fun updateUi() {
        navController.navigate(R.id.action_fragmentSignIn_to_mainNavActivity)
        requireActivity().finish()
    }
}