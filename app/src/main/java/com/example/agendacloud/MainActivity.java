package com.example.agendacloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_login, btn_singup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // referenciamos los elementos
        btn_login = findViewById(R.id.btn_login);
        btn_singup = findViewById(R.id.btn_singup);

        // asignamos los gestores de eventos de la UI

        // Onclick ve a login
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        // Onclick ve a registro
        btn_singup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, SingUp.class));
            }
        });

    }
}