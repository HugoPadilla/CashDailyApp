package com.wenitech.cashdaily.ActivityMain.ActivityClientes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.wenitech.cashdaily.ActivityMain.ActivityClientes.Fragment.VencidosFragment;
import com.wenitech.cashdaily.ActivityMain.ActivityClientes.Fragment.TodosFragment;
import com.wenitech.cashdaily.ActivityMain.ActivityClientes.Fragment.CobrarHoyFragment;
import com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityNuevoCliente.NewClientActivity;
import com.wenitech.cashdaily.R;

public class ListaClientesActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabButtomNewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);
        settingToolbar();
        settingTabLayout();
        settingViewListener();

    }

    private void settingViewListener() {
        fabButtomNewClient = findViewById(R.id.fab_button_nuevo_cliente);
        fabButtomNewClient.setOnClickListener(this);
    }

    private void settingToolbar() {
        toolbar = findViewById(R.id.toolbar_lista_clientes);
        toolbar.setTitle("Lista de clientes");
        toolbar.setNavigationIcon(R.drawable.ic_system_flecha_atras_blanco);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_lista_clientes,menu);
        return true;
    }

    private void settingTabLayout() {
        viewPager = findViewById(R.id.view_pager_lista_clientes);
        PagerAdapter pagerAdapter = new viewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tab_layout_lista_clientes);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_button_nuevo_cliente:
                startActivity(new Intent(ListaClientesActivity.this, NewClientActivity.class));
            break;
        }
    }

    public class viewPagerAdapter extends FragmentPagerAdapter{
        // Adactador viepager para tabs
        public viewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new CobrarHoyFragment();
                case 1:
                    return new VencidosFragment();
                default:
                    return new TodosFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Cobrar Hoy";
                case 1:
                    return "Vencidos";
                default:
                    return "Todos";
            }
        }
    }
}
