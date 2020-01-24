package com.wenitech.cashdaily.ActivityLogin.ActivityRecuperaContraseña;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.wenitech.cashdaily.R;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity implements InterfaceResetPasswordActivity.View {

    private InterfaceResetPasswordActivity.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mPresenter = new ResetPasswordPresenter(this);

        // Configurar views
        iniciarToolbar();
    }

    private void iniciarToolbar() {
        Toolbar toolbarActivity = findViewById(R.id.toolbar);
        toolbarActivity.setTitle("Recuperar cuenta");
        setSupportActionBar(toolbarActivity);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
