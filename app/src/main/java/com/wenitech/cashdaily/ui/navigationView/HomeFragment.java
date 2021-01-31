package com.wenitech.cashdaily.ui.navigationView;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.databinding.HomeFragmentBinding;
import com.wenitech.cashdaily.viewModel.MainFragmentViewModel;
import com.wenitech.cashdaily.R;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private MainFragmentViewModel viewModel;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private NavController navController;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        navController = Navigation.findNavController(requireView());
        viewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
    }
}