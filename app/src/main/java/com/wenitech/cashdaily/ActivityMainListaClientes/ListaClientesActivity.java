package com.wenitech.cashdaily.ActivityMainListaClientes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.wenitech.cashdaily.ActivityMain.Fragment.ClientesFragment;
import com.wenitech.cashdaily.ActivityMain.Fragment.GastosFragment;
import com.wenitech.cashdaily.ActivityMain.Fragment.HomeFragment;
import com.wenitech.cashdaily.R;

import static com.wenitech.cashdaily.R.drawable.ic_system_flecha_blanco;
import static com.wenitech.cashdaily.R.drawable.ic_systema_overflow_blanco;

public class ListaClientesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabNewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);
        settingToolbar();
        settingTabLayout();

    }

    private void settingToolbar() {
        toolbar = findViewById(R.id.toolbar_lista_clientes);
        toolbar.setTitle("Lista de clientes");
        toolbar.setNavigationIcon(R.drawable.ic_system_flecha_blanco);
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
                    return new HomeFragment();
                case 1:
                    return new ClientesFragment();
                default:
                    return new GastosFragment();
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
