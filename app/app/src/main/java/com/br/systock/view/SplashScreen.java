package com.br.systock.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.br.systock.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMainActivity();
            }
        }, 2000);
    }

    private void showMainActivity() {
        Intent intent = new Intent(
                SplashScreen.this, LoginActivity.class
        );
        startActivity(intent);
        finish();
    }


}