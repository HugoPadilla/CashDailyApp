package com.wenitech.cashdaily.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.databinding.ActivityNavMainBinding;

import java.util.Objects;

public class MainNavActivity extends AppCompatActivity {

    private ActivityNavMainBinding binding;
    private NavController navController;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setting dataBinding
        binding = ActivityNavMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Firebase auth listener
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    updateUi();
                }
            }
        };

        // instance of nav controller
        navController = Navigation.findNavController(this, R.id.fragment);
        //appbar configuration of drawer
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(navController.getGraph())
                .setDrawerLayout(binding.drawerLayoutMainActivity)
                .build();

        NavigationUI.setupWithNavController(binding.toolbarMain, navController, appBarConfiguration); //setting Toolbar
        NavigationUI.setupWithNavController(binding.navigationViewMainActivity, navController); //setting navigation view

        inicializarDatosNavigationDrawerHeader();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // check if user is login
        if (mAuth.getCurrentUser() == null) {
            updateUi();
        } else {
            mAuth.addAuthStateListener(authStateListener);
        }
    }

    private void inicializarDatosNavigationDrawerHeader() {
        View navHeader = binding.navigationViewMainActivity.getHeaderView(0);  //instance of header drawer

        ImageView imageView = navHeader.findViewById(R.id.image_view_drawer_perfil);
        TextView textViewDrawerNombre = navHeader.findViewById(R.id.text_view_drawer_name);
        TextView textViewDrawerCorreo = navHeader.findViewById(R.id.text_view_drawer_email);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogLSessionClose();
            }
        });

        if (mUser != null) {
            String nombreUsuario = mUser.getDisplayName();
            String correoUsuario = mUser.getEmail();
            textViewDrawerNombre.setText(nombreUsuario);
            textViewDrawerCorreo.setText(correoUsuario);
        }
    }

    private void openDialogLSessionClose() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Cerrar sesion")
                .setMessage("Estas a punto de salir de tu cuenta y es posible que exista alguna informacion sin guardar en la nube. Asegurate de haber sincronizado todo antes de salir.")
                .setPositiveButton("Cerrar sesion", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    private void updateUi() {
        navController.navigate(R.id.activityNavLogin);
        finish();
    }
}
