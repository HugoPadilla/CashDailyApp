package com.wenitech.cashdaily.framework.features.route

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wenitech.cashdaily.R

class RoutesFragment : Fragment() {

    companion object {
        fun newInstance() = RoutesFragment()
    }

    private lateinit var viewModel: RoutesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.routes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RoutesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}