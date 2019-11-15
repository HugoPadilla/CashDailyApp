package com.wenitech.cashdaily.ClienteDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wenitech.cashdaily.ClienteDetail.FragmentNavigation.CreditoNavFragment;
import com.wenitech.cashdaily.ClienteDetail.FragmentNavigation.HistorialNavFragment;
import com.wenitech.cashdaily.ClienteDetail.FragmentNavigation.PerfilNavFragment;
import com.wenitech.cashdaily.NewCreditActivity.NewCreditActivity;
import com.wenitech.cashdaily.R;

public class ClienteDetailActivity extends AppCompatActivity implements ClienteDetailInterface.view {

    private ClienteDetailInterface.presenter presenter;
    private  String string;

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

        // se obtiene el string del la referencia del cliente
        string = getIntent().getStringExtra("id_cliente_ref");

        Bundle args = new Bundle();
        args.putString("id_cliente_ref_credito", string);
        CreditoNavFragment creditoNavFragment = new CreditoNavFragment();
        creditoNavFragment.setArguments(args);

        //Pestana del Navigation view por depecto
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,creditoNavFragment).commit();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectfragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_credito:
                            Bundle args = new Bundle();
                            args.putString("id_cliente_ref_credito", string);
                            selectfragment = new CreditoNavFragment();
                            selectfragment.setArguments(args);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuItemNewCredit:
                Intent intent = new Intent(ClienteDetailActivity.this, NewCreditActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
