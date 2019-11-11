package com.wenitech.cashdaily.ClienteDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wenitech.cashdaily.ClienteDetail.FragmentNavigation.CreditoNavFragment;
import com.wenitech.cashdaily.ClienteDetail.FragmentNavigation.HistorialNavFragment;
import com.wenitech.cashdaily.ClienteDetail.FragmentNavigation.PerfilNavFragment;
import com.wenitech.cashdaily.R;

public class ClienteDetailActivity extends AppCompatActivity implements ClienteDetailInterface.view {

    private ClienteDetailInterface.presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detail);
        presenter = new ClienteDetailPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar_cliente_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Iniciando Navigatio view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavClienteDetail);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);
        //Pestana del Navigation view por depecto
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CreditoNavFragment()).commit();

        //obtener el putExtra referencia base de datos del cliente
        String documentrefrence = getIntent().getStringExtra("item_client");
        TextView textView = findViewById(R.id.textView22);
        textView.setText(documentrefrence);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectfragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_credito:
                            selectfragment = new CreditoNavFragment();
                            break;
                        case R.id.nav_historial:
                            selectfragment = new HistorialNavFragment();
                            break;
                        case R.id.nav_perfil:
                            selectfragment = new PerfilNavFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectfragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
