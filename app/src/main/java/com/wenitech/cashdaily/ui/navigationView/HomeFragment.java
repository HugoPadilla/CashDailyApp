package com.wenitech.cashdaily.ui.navigationView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.Data.model.Caja;
import com.wenitech.cashdaily.Data.remoteDatabase.MyFirebaseAuth;
import com.wenitech.cashdaily.databinding.HomeFragmentBinding;
import com.wenitech.cashdaily.viewModel.HomeFragmentViewModel;

import java.text.NumberFormat;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private HomeFragmentViewModel homeFragmentViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = Navigation.findNavController(requireView());
        initTodoViewModel();
        getUserBox();
    }

    private void initTodoViewModel() {
        homeFragmentViewModel = new ViewModelProvider(requireActivity()).get(HomeFragmentViewModel.class);
    }

    /**
     * Llama al metodo getBox de ViewModel y le pasa una instanacia de FirebaseUser
     * Luego observa el Objeto cajaLiveData expuesto por el viewModel y actualiza la UI
     */
    private void getUserBox() {
        MyFirebaseAuth authAPI = MyFirebaseAuth.getInstance();
        FirebaseUser user = authAPI.getAuth().getCurrentUser();

        if (user != null) {
            homeFragmentViewModel.getUserBox(user);
            homeFragmentViewModel.cajaLiveData.observe(getViewLifecycleOwner(), new Observer<Caja>() {
                @Override
                public void onChanged(Caja caja) {
                    NumberFormat formatMoney = NumberFormat.getCurrencyInstance();
                    binding.textViewCajaDisponible.setText(formatMoney.format(caja.getTotalCaja()));
                }
            });
        }
    }
}