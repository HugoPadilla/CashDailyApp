package com.wenitech.cashdaily.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wenitech.cashdaily.LoginActivity.LoginActivity;
import com.wenitech.cashdaily.MainActivity.Fragment.ClientesFragment;
import com.wenitech.cashdaily.MainActivity.Fragment.GastosFragment;
import com.wenitech.cashdaily.MainActivity.Fragment.HomeFragment;
import com.wenitech.cashdaily.NewClient.NewClientActivity;
import com.wenitech.cashdaily.NewGastoActivity.NewGastoActivity;
import com.wenitech.cashdaily.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FloatingActionButton fabNewClient, fabNewGasto;

    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Add Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cash Daily App");
        setSupportActionBar(toolbar);
        //Add Tanlayou and View Pager
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new mAdacter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        //Configurar el selector de tab para cambiar el Fab
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showFabCorecto(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //iniciar Fab por defecto



        //Fab Add OnClik Lisstener
        fabNewClient = findViewById(R.id.fabNewCliente);
        fabNewClient.setOnClickListener(this);
        fabNewGasto = findViewById(R.id.fabNewGasto);
        fabNewGasto.setOnClickListener(this);

    }
    //metodo para configurar el Fab sque se debe mostrar
    private void showFabCorecto(int position) {
        switch (position){
            case 0:
                fabNewGasto.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                    @Override
                    public void onHidden(FloatingActionButton fab) {
                        fabNewClient.show();
                    }
                });
                break;
            case 1:
                fabNewGasto.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                    @Override
                    public void onHidden(FloatingActionButton fab) {
                        fabNewClient.show();
                    }
                });
                break;
            case 2:
                fabNewClient.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                    @Override
                    public void onHidden(FloatingActionButton fab) {
                        fabNewGasto.show();
                    }
                });


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuItenPerfil){

        }else if (item.getItemId() == R.id.menuItemAbout){

        } else if (item.getItemId() == R.id.menuItenSingOut){
            mAuth.signOut();
            UpdateUi(mAuth.getCurrentUser());
        }
            return super.onOptionsItemSelected(item);


    }

    @Override
    public void onClick(View v) {
         int i = v.getId();

         if (i == R.id.fabNewCliente){

             Intent intent = new Intent(MainActivity.this, NewClientActivity.class);
             startActivity(intent);

         } if (i == R.id.fabNewGasto){
            Intent intent = new Intent(MainActivity.this, NewGastoActivity.class);
            Bundle options = ActivityOptions.makeSceneTransitionAnimation( MainActivity.this,
                    fabNewGasto,"newGasto").toBundle();
            startActivity(intent,options);
        }

    }

    public class mAdacter extends FragmentPagerAdapter{

        public mAdacter(@NonNull FragmentManager fm) {
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
                    return "Inicio";
                case 1:
                    return "Clientes";
                default:
                    return "Gastos";
            }
        }
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
