package com.wenitech.cashdaily.features.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.data.model.Cliente
import com.wenitech.cashdaily.databinding.FragmentCobrarHoyBinding
import com.wenitech.cashdaily.features.client.ui.adapter.RecyclerViewClienteAdapter
import java.text.SimpleDateFormat
import java.util.*

class CobrarHoyFragment : Fragment(), RecyclerViewClienteAdapter.ReciclerViewClienteInteface{

    private lateinit var _binding: FragmentCobrarHoyBinding
    private val binding get() = _binding

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    val date = "02-05-2021"
    val date1 = dateFormat.parse(date)
    val startDate = Timestamp(date1)

    private val ListaClienteCollection = db.collection("usuarios").document(auth.uid!!).collection("clientes").whereLessThan("fechaCobro", startDate)
    var recyclerViewClienteAdapter: RecyclerViewClienteAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentCobrarHoyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewClientCobrarHoy()
    }

    private fun setupRecyclerViewClientCobrarHoy() {
        val query = ListaClienteCollection.orderBy("fullName", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query, Cliente::class.java).build()


        recyclerViewClienteAdapter = RecyclerViewClienteAdapter(options, this);
        val resId = R.anim.layout_animation_from_bottom
        val animation = AnimationUtils.loadLayoutAnimation(context, resId)

        binding.recyclerViewCobrarHoy.layoutAnimation = animation
        binding.recyclerViewCobrarHoy.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCobrarHoy.adapter = recyclerViewClienteAdapter
    }

    override fun onStart() {
        super.onStart()
        recyclerViewClienteAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        recyclerViewClienteAdapter!!.stopListening()
    }

    override fun onClientClicked(cardView: View?, cliente: Cliente?) {
       Snackbar.make(cardView!!, "Click on ${cliente!!.fullName}", Snackbar.LENGTH_SHORT).show()
    }

}
