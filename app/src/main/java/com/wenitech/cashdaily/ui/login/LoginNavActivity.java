package com.wenitech.cashdaily.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.wenitech.cashdaily.R;

public class LoginNavActivity extends AppCompatActivity{

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_login);
    }

}
