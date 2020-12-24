package com.wenitech.cashdaily.ui.navigationView;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenitech.cashdaily.R;

public class ConfiguracionFragment extends Fragment {

    private ConfiguracionViewModel mViewModel;

    public static ConfiguracionFragment newInstance() {
        return new ConfiguracionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.configuracion_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ConfiguracionViewModel.class);
        // TODO: Use the ViewModel
    }

}