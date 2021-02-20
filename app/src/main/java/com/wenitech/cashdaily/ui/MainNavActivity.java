package com.wenitech.cashdaily.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.databinding.ActivityNavMainBinding;
import com.wenitech.cashdaily.viewModel.TodoViewModel;

public class MainNavActivity extends AppCompatActivity {

    private ActivityNavMainBinding binding;
    private TodoViewModel todoViewModel;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initFirebaseAuth();
        initNavController();
        initTodoViewModel();
        setupFirebaseAuthListener();
        setupWithNavController();
        setupOnDestinationListenerNavController();
    }

    private void initNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        navController = navHostFragment.getNavController();
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void initTodoViewModel() {
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
    }

    private void setupOnDestinationListenerNavController() {
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.customerCreditFragment) {
                    binding.bottomNavigationView.setVisibility(View.GONE);
                } else {
                    binding.bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

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

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    private void updateUi() {
        navController.navigate(R.id.activityNavLogin);
        finish();
    }

    private void setupFirebaseAuthListener() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    updateUi();
                }
            }
        };
    }

    private void setupWithNavController() {
        appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.homeFragment, R.id.clientFragment, R.id.cajaFragment, R.id.informeFragment)
                .setDrawerLayout(binding.drawerLayoutMainActivity)
                .build();

        NavigationUI.setupWithNavController(binding.toolbarMain, navController, appBarConfiguration); //setting Toolbar
        NavigationUI.setupWithNavController(binding.navigationViewMainActivity, navController); //setting navigation view
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        inicializarDatosNavigationDrawerHeader();
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
                        signOut();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void signOut() {
        //todoViewModel.cajaLiveData.removeObservers(this);
        mAuth.signOut();
        finish();
    }
}
