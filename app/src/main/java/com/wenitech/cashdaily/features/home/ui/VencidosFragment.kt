package com.wenitech.cashdaily.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.data.model.Cliente
import com.wenitech.cashdaily.features.client.ui.adapter.RecyclerViewClienteAdapter

class VencidosFragment : Fragment(), RecyclerViewClienteAdapter.ReciclerViewClienteInteface {

    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val ClientesRef = db.collection("usuarios").document(mAuth.uid!!).collection("clientes")
    private var mRecyclerviewClienteAdapter: RecyclerViewClienteAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_vencidos, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = ClientesRef.orderBy("fullName", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Cliente>().setQuery(query, Cliente::class.java).build()

        val mRecyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        mRecyclerviewClienteAdapter = RecyclerViewClienteAdapter(options, this)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mRecyclerviewClienteAdapter
    }

    override fun onStart() {
        super.onStart()
        mRecyclerviewClienteAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        mRecyclerviewClienteAdapter!!.stopListening()
    }

    override fun onClientClicked(cardView: View?, cliente: Cliente?) {
        Toast.makeText(requireContext(), cliente!!.fullName.toString(), Toast.LENGTH_SHORT).show()
    }
}