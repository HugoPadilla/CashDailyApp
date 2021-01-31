package com.wenitech.cashdaily;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wenitech.cashdaily.databinding.FragmentCustomerCreditBinding;

public class CustomerCreditFragment extends Fragment {

    private FragmentCustomerCreditBinding binding;

    public CustomerCreditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerCreditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomerCreditFragmentArgs args = CustomerCreditFragmentArgs.fromBundle(getArguments());
        if (args.getIsCreditActive()){
            binding.groupContentCustomer.setVisibility(View.VISIBLE);
            binding.groupCustomerVacio.setVisibility(View.GONE);
        }else {
            binding.groupContentCustomer.setVisibility(View.GONE);
            binding.groupCustomerVacio.setVisibility(View.VISIBLE);
        }

    }
}