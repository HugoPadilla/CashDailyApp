package com.wenitech.cashdaily.NewGastoActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wenitech.cashdaily.R;

public class NewGastoActivity extends AppCompatActivity implements InterfaceNewGasto.view{

    protected InterfaceNewGasto.presenter presenter;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gasto);
        presenter = new NewGastoPresenter(this);
        addToolbar();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbarNewClient);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
