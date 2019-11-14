package com.wenitech.cashdaily.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.wenitech.cashdaily.Model.Credito;

public class AdapterRecyclerCredito extends FirestoreRecyclerAdapter<Credito,AdapterRecyclerCredito.mViewHolder> {

    public AdapterRecyclerCredito(@NonNull FirestoreRecyclerOptions<Credito> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull Credito model) {

    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class mViewHolder extends RecyclerView.ViewHolder{



        public mViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
