package com.wenitech.cashdaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityNuevoCredito.NewCreditActivity;

public class CreditoVacioActivity extends AppCompatActivity implements View.OnClickListener {

    private ExtendedFloatingActionButton fabButtomNuevoCredito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credito_vacio);
        iniciarToobar();
        fabButtomNuevoCredito = findViewById(R.id.fab_buttom_credito_vacio_nuevo_credito);
        fabButtomNuevoCredito.setOnClickListener(this);
    }

    private void iniciarToobar() {
        Toolbar toolbar = findViewById(R.id.toolbar_credito_vacio);
        toolbar.setSubtitle(getIntent().getStringExtra("CLIENTE_INTENT_NOMBRE"));
        toolbar.setNavigationIcon(R.drawable.ic_system_flecha_atras_blanco);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar opciones de menu
        getMenuInflater().inflate(R.menu.menu_credito_vacio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_credito_vacio_perfil:
                startActivity(new Intent(CreditoVacioActivity.this,PerfilClienteActivity.class));
                break;
            case R.id.menu_item_credito_vacio_llamar:

                break;
            case R.id.menu_item_credito_vacio_historial:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_buttom_credito_vacio_nuevo_credito:
                Intent intent =new Intent(CreditoVacioActivity.this, NewCreditActivity.class);
                intent.putExtra("REFERENCIA_CLIENTE",getIntent().getStringExtra("REFERENCIA_CLIENTE"));
                intent.putExtra("CLIENTE_INTENT_NOMBRE",getIntent().getStringExtra("REFERENCIA_CLIENTE"));
                startActivity(intent);
                break;
        }
    }
}
