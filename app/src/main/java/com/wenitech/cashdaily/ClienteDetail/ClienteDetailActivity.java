package com.wenitech.cashdaily.ClienteDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.wenitech.cashdaily.R;

public class ClienteDetailActivity extends AppCompatActivity implements ClienteDetailInterface.view {

    private ClienteDetailInterface.presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detail);
        presenter = new ClienteDetailPresenter(this);

        String documentrefrence = getIntent().getStringExtra("item_client");
        TextView textView = findViewById(R.id.textView22);
        textView.setText(documentrefrence);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
