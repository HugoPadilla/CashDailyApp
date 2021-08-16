package com.wenitech.cashdaily.framework.features.credit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wenitech.cashdaily.R

class CreditsFragment : Fragment() {

    companion object {
        fun newInstance() = CreditsFragment()
    }

    private lateinit var viewModel: CreditsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.credits_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreditsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}