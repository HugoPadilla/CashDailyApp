package com.wenitech.cashdaily.framework.features.userApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.features.userApp.viewModel.SettingViewModel

class SettingsFragment : Fragment() {

    private val mViewModel by viewModels<SettingViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}