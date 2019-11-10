package com.wenitech.cashdaily.MainActivity.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wenitech.cashdaily.Adapter.RecyclerViewHomeAdapter;
import com.wenitech.cashdaily.Model.Cliente;
import com.wenitech.cashdaily.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void clikfecha (View v){
        Toast.makeText(getActivity(), "Agregar fecha", Toast.LENGTH_SHORT).show();
    }
    
}
