package com.wenitech.cashdaily.framework.features.client.profileClient

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wenitech.cashdaily.R

class ProfileClientFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileClientFragment()
    }

    private lateinit var viewModel: ProfileClientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_client_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileClientViewModel::class.java)
        // TODO: Use the ViewModel
    }

}