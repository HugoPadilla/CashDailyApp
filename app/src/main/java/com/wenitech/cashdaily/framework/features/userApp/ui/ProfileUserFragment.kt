package com.wenitech.cashdaily.framework.features.userApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.UserProfileChangeRequest
import com.wenitech.cashdaily.R
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
        profileUserViewModel.userModelAppLiveData.observe(viewLifecycleOwner, Observer { resources ->
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

    /**
     * Dialog for replace FullName
     */
    private fun openDialogChangeFullName() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialogo_cambiar_nombre_usuario, null)
        val editTextNuevoNombre: TextInputEditText = view.findViewById(R.id.edit_text_dialogo_nombre_usuario)
        builder.setView(view)
        builder.setTitle("Cambiar nombre")
        builder.setMessage("Este nombre sera visible para tus cobradores y puede que se use en algunas funciones de la App.")
        builder.setPositiveButton("Actualizar") { dialog, which ->
            val nuevoNombre = editTextNuevoNombre.text.toString().trim { it <= ' ' }
            if (nuevoNombre.isEmpty()) {
                Toast.makeText(requireContext(), "No se han hecho cambios", Toast.LENGTH_SHORT).show()
            } else if (nuevoNombre.length <= 3) {
                Toast.makeText(requireContext(), "Escribe al menos 3 letras", Toast.LENGTH_SHORT).show()
            } else if (nuevoNombre.length >= 35) {
                Toast.makeText(requireContext(), "Nombre demasiado grande", Toast.LENGTH_SHORT).show()
            } else {
                val update = UserProfileChangeRequest.Builder().setDisplayName(nuevoNombre).build()

            }
        }
        builder.setNegativeButton("Cancelar") { dialog, which -> Toast.makeText(requireContext(), "Cancelar", Toast.LENGTH_SHORT).show() }
        val dialog = builder.create()
        dialog.show()
    }
}