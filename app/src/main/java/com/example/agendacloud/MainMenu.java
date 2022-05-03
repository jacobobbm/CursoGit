package com.example.agendacloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenu extends AppCompatActivity {
    Button btnLogout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btnLogout = findViewById(R.id.log_out);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                logOut();
            }
        });

    }

    private void logOut() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainMenu.this, MainActivity.class ));
        Toast.makeText(this, "log out realizado sin problemas", Toast.LENGTH_SHORT).show();

    }
}