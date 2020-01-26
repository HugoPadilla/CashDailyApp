package com.wenitech.cashdaily.ActivityMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.ActivityLogin.ActivityIniciarSesion.LoginActivity;
import com.wenitech.cashdaily.ActivityMain.ActivityCaja.CajaActivity;
import com.wenitech.cashdaily.ActivityMain.ActivityClientes.ListaClientesActivity;
import com.wenitech.cashdaily.ActivityLogin.ActivityInicioSesion.InicioSesionActivity;
import com.wenitech.cashdaily.ActivityMain.ActivityCobradores.CobradorActivity;
import com.wenitech.cashdaily.ActivityMain.ActivityEstadisticas.EstadisticasActivity;
import com.wenitech.cashdaily.ActivityMain.ActivityGastos.GastosActivity;
import com.wenitech.cashdaily.ActivityMain.ActivityProductosYServicios.ProductosActivity;
import com.wenitech.cashdaily.ActivityMain.ActivitySincronizacion.SincronizacionActivity;
import com.wenitech.cashdaily.ActivitysNavigationDrawer.ActivityAcercaDe.AcercaActivity;
import com.wenitech.cashdaily.ActivitysNavigationDrawer.ActivityAyuda.AyudaActivity;
import com.wenitech.cashdaily.ActivitysNavigationDrawer.ActivityPerfilUsuario.PerfilUsuarioActivity;
import com.wenitech.cashdaily.ActivitysNavigationDrawer.ActivitySuscripcion.SuscripcionActivity;
import com.wenitech.cashdaily.ActivitysNavigationDrawer.ActivityTerminos.TerminosActivity;
import com.wenitech.cashdaily.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;

    // Todo: declara navigation drwer
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    // Todo: declara view navigation header
    private TextView textViewDrawerNombre;
    private TextView textViewDrawerInicialNombre;
    private TextView textViewDrawerCorreo;

    // Todo: declarar cardview main activity
    private CardView cardViewClientes;
    private CardView cardViewEstaditicas;
    private CardView cardViewCaja;
    private CardView cardViewGastos;
    private CardView cardViewProductos;
    private CardView cardViewCobradores;

    // Todo: Obejos Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        agregarToolbar();
        ConfigurarNavigationDrawer();
        castingCardView();
    }

    private void inicializarDatosNavigationDrawerHeader() {
        if (mUser != null){
            String nombreUsuario = mUser.getDisplayName();
            String correoUsuario = mUser.getEmail();
            textViewDrawerNombre.setText(nombreUsuario);
            textViewDrawerCorreo.setText(correoUsuario);
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                textViewDrawerInicialNombre.setText(nombreUsuario.substring(0, 1).toUpperCase());
            }


        }
    }

    private void castingCardView() {
        cardViewClientes = findViewById(R.id.card_view_clientes);
        cardViewClientes.setOnClickListener(this);
        cardViewEstaditicas = findViewById(R.id.card_view_estadisticas);
        cardViewEstaditicas.setOnClickListener(this);
        cardViewCaja = findViewById(R.id.card_view_caja);
        cardViewCaja.setOnClickListener(this);
        cardViewGastos = findViewById(R.id.card_view_gastos);
        cardViewGastos.setOnClickListener(this);
        cardViewProductos = findViewById(R.id.card_view_productos);
        cardViewProductos.setOnClickListener(this);
        cardViewCobradores = findViewById(R.id.card_view_cobradores);
        cardViewCobradores.setOnClickListener(this);
    }

    private void agregarToolbar() {
        // Agregar  Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cash Daily App");
        setSupportActionBar(toolbar);
    }
    private void ConfigurarNavigationDrawer() {
        // Todo: casting navigation drawer
        mDrawerLayout = findViewById(R.id.drawer_layout_main_activity);
        mNavigationView = findViewById(R.id.navigation_view_main_activity);

        // Casting view navigation header
        View drawerHeader = mNavigationView.getHeaderView(0);
        textViewDrawerNombre = drawerHeader.findViewById(R.id.text_view_drawer_name);
        textViewDrawerInicialNombre = drawerHeader.findViewById(R.id.text_view_drawer_inicial);
        textViewDrawerCorreo = drawerHeader.findViewById(R.id.text_view_drawer_email);

        // Configurar abrir y cerrar navigation drawer
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.syncState();

        // Navigatio view Listener
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar opciones de menu
        getMenuInflater().inflate(R.menu.menu_main_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // verificar item del menu del toolbar
        switch (item.getItemId()){
            case R.id.menu_iten_clound:
                Toast.makeText(this, "clound", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_sincronizacion:
                Intent intent = new Intent(MainActivity.this, SincronizacionActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.menu_iten_tema:
                Toast.makeText(this, "thema", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_salir:
                Toast.makeText(this, "Salir", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Verificar elemento selecionado item naigation drawer
        switch (menuItem.getItemId()){
            case R.id.menu_iten_drawer_perfil:
                Intent intentPerfil = new Intent(MainActivity.this, PerfilUsuarioActivity.class);
                startActivity(intentPerfil, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            break;
            case R.id.menu_iten_drawer_suscripcion:
                Intent intentSuscripcio = new Intent(MainActivity.this, SuscripcionActivity.class);
                startActivity(intentSuscripcio, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.menu_iten_drawer_configuracion:
                Toast.makeText(this, "navigation item configuracion", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_drawer_compartir:
                Toast.makeText(this, "navigation item compartir", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_drawer_calificar_app:
                Toast.makeText(this, "navigation item calificar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_iten_drawer_ayuda:
                Intent intentAyuda = new Intent(MainActivity.this, AyudaActivity.class);
                startActivity(intentAyuda, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.menu_iten_drawer_terminos:
                Intent intentTerminos = new Intent(MainActivity.this, TerminosActivity.class);
                startActivity(intentTerminos, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.menu_iten_drawer_acerca_de:
                Intent intentAcerca = new Intent(MainActivity.this, AcercaActivity.class);
                startActivity(intentAcerca, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.menu_iten_drawer_cerrar_sesion:
                mAuth.signOut();
                UpdateUi(mAuth.getCurrentUser());
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        // Click listener
        switch (v.getId()){
            case R.id.card_view_clientes:
                Intent intentClientes = new Intent(MainActivity.this, ListaClientesActivity.class);
                startActivity(intentClientes, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.card_view_estadisticas:
                Intent intentEstadisticas = new Intent(MainActivity.this, EstadisticasActivity.class);
                startActivity(intentEstadisticas, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.card_view_caja:
                Intent intentCaja = new Intent(MainActivity.this, CajaActivity.class);
                startActivity(intentCaja, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.card_view_gastos:
                Intent intentGastos = new Intent(MainActivity.this, GastosActivity.class);
                startActivity(intentGastos, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.card_view_productos:
                Intent intentProductos = new Intent(MainActivity.this, ProductosActivity.class);
                startActivity(intentProductos, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.card_view_cobradores:
                Intent intentCobradores = new Intent(MainActivity.this, CobradorActivity.class);
                startActivity(intentCobradores, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        UpdateUi(mAuth.getCurrentUser());
        inicializarDatosNavigationDrawerHeader();
    }

    private void UpdateUi(FirebaseUser user){
        if (user == null){
            Intent intent = new Intent(MainActivity.this, InicioSesionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
