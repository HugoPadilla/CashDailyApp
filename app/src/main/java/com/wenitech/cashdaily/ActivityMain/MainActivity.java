package com.wenitech.cashdaily.ActivityMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.ActivityLogin.LoginActivity;
import com.wenitech.cashdaily.ActivityMainListaClientes.ListaClientesActivity;
import com.wenitech.cashdaily.DetallesClienteActivity.ClienteDetailActivity;
import com.wenitech.cashdaily.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private ConstraintLayout mConstraintLayout;

    final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agregarToolbar();
        ConfigurarNavigationDrawer();
        mConstraintLayout = findViewById(R.id.constrain_layout_main_activity);
        castingCardView();
    }

    private void castingCardView() {
        ConstraintLayout card_view_clientes = findViewById(R.id.card_view_clientes);
        card_view_clientes.setOnClickListener(this);
    }

    private void agregarToolbar() {
        // Agregar  Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cash Daily App");
        setSupportActionBar(toolbar);
    }
    private void ConfigurarNavigationDrawer() {
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout_main_activity);
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.syncState();

        // Navigatio view Listener
        NavigationView mNavigationView = findViewById(R.id.navigation_view_main_activity);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

   // Inflar opciones de menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar,menu);
        return true;
    }
    // chekear que elemento del menu se ha presionado
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_iten_clound:
                Intent intent = new Intent(MainActivity.this, ClienteDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_iten_sincronizacion:
                Toast.makeText(this, "Sincronizacion clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_tema:
                Toast.makeText(this, "tema clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_salir:
                Toast.makeText(this, "Salir clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_iten_drawer_perfil:
                Toast.makeText(this, "navigation item perfil", Toast.LENGTH_SHORT).show();
            break;
            case R.id.menu_iten_drawer_configuracion:
                Toast.makeText(this, "navigation item configuracion", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_drawer_suscripcion:
                Toast.makeText(this, "navigation item suscripcion", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_drawer_compartir:
                Toast.makeText(this, "navigation item compartir", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_drawer_calificar_app:
                Toast.makeText(this, "navigation item calificar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_drawer_cerrar_sesion:
                Toast.makeText(this, "navigation item cerrar sesion", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_drawer_ayuda:
                Toast.makeText(this, "navigation item ayuda", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_drawer_terminos:
                Toast.makeText(this, "navigation item terminos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_drawer_acerca_de:
                Toast.makeText(this, "navigation item acerda de la app", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_view_clientes:
                Intent intent = new Intent(MainActivity.this, ListaClientesActivity.class);
                startActivity(intent);
                break;
        }

         /*if (i == R.id.fabNewGasto){
           Intent intent = new Intent(MainActivity.this, NewGastoActivity.class);
           Bundle options = ActivityOptions.makeSceneTransitionAnimation( MainActivity.this,
                    fabNewGasto,"newGasto").toBundle();
            startActivity(intent,options);
        }
        */
    }


    @Override
    protected void onStart() {
        super.onStart();
        UpdateUi(mAuth.getCurrentUser());
    }

    private void UpdateUi(FirebaseUser user){
        if (user == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
