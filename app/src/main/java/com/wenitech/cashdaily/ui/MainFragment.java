package com.wenitech.cashdaily.ui;

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
import com.wenitech.cashdaily.databinding.MainFragmentBinding;
import com.wenitech.cashdaily.viewModel.MainFragmentViewModel;
import com.wenitech.cashdaily.R;

import java.util.Objects;

public class MainFragment extends Fragment {

    private MainFragmentBinding binding;
    private MainFragmentViewModel viewModel;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private NavController navController;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        navController = Navigation.findNavController(requireView());
        viewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_mainFragment_to_listaClientesActivity);
            }
        });

        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_mainFragment_to_cajaActivity);
            }
        });

        binding.cardView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_mainFragment_to_nav_graph_login);
            }
        });

    }



}