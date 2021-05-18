package com.wenitech.cashdaily.features.userApp.ui

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
import com.wenitech.cashdaily.data.firebase.MyFirebaseAuth
import com.wenitech.cashdaily.databinding.FragmentPerfilBinding
import com.wenitech.cashdaily.features.userApp.viewModel.ProfileUserViewModel
import com.wenitech.cashdaily.core.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileUserFragment : Fragment() {

    private val profileUserViewModel by viewModels<ProfileUserViewModel>()

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private var myAuth: MyFirebaseAuth? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPerfilBinding.inflate(LayoutInflater.from(inflater.context), container, false)

        myAuth = MyFirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDateOfUserOnView()
    }


    private fun setupDateOfUserOnView() {
        profileUserViewModel.userAppLiveData.observe(viewLifecycleOwner, Observer {
            // Todo: Implementar Observador
            when (it.status) {
                Status.INIT -> TODO()
                Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    binding.textViewFullName.text = it.dato!!.nameBussine
                    binding.textViewEmail.text = myAuth!!.user.email

                }
                Status.COLLICION -> TODO()
                Status.FAILED -> TODO()
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error: ${it.messenger}", Toast.LENGTH_SHORT).show()
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
                myAuth!!.user.updateProfile(update).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        setupDateOfUserOnView()
                        Toast.makeText(requireContext(), "Nombre Actualizado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "No fue posible cambiar los datos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, which -> Toast.makeText(requireContext(), "Cancelar", Toast.LENGTH_SHORT).show() }
        val dialog = builder.create()
        dialog.show()
    }
}