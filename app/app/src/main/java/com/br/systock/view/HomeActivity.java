package com.br.systock.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.br.systock.R;

public class HomeActivity extends AppCompatActivity {
    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button buttonOne = findViewById(R.id.button7);
        buttonOne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent abrircadastrodetipos = new Intent(getApplicationContext(), TypeRegister.class);
                startActivity(abrircadastrodetipos);

            }
        });
        Button buttonTwo = findViewById(R.id.button5);
        buttonTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v1) {

                Intent abrirlistagemdetipos = new Intent(getApplicationContext(), TypeList.class);
                startActivity(abrirlistagemdetipos);

            }
        });
        Button buttonThree = findViewById(R.id.button4);
        buttonThree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v2) {

                Intent abrirCadatroDeProdutos = new Intent(getApplicationContext(), ProductRegistrationActivity.class);
                startActivity(abrirCadatroDeProdutos);

            }
        });
    }
}