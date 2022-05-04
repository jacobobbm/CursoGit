package com.example.agendacloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendacloud.AddNoteLogic.AddNote;
import com.example.agendacloud.ShowNotes.ShowNotes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity {
    Button btnLogout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView userName, userMail, uid;
    ProgressBar progressBar;

    Button btnAdd, btnList, btnArchived, btnProfile, btnAbout;

    DatabaseReference usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

//        btnLogout = findViewById(R.id.log_out);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        userName = findViewById(R.id.main_menu_username);
        userMail = findViewById(R.id.main_menu_user_mail);
        uid = findViewById(R.id.main_menu_uid);
        progressBar = findViewById(R.id.main_menu_progressbar);

        usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");


        btnAdd = findViewById(R.id.main_menu_add_note);
        btnList= findViewById(R.id.main_menu_show_notes);
        btnArchived = findViewById(R.id.main_menu_archived);
        btnProfile  = findViewById(R.id.main_menu_profile);
        btnAbout = findViewById(R.id.main_menu_about);

        btnLogout = findViewById(R.id.main_menu_log_out);

        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                logOut();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uidTxt = uid.getText().toString();
                String correoTxt = userMail.getText().toString();

                /* pasar info a actividad addNote*/
//                startActivity(new Intent(MainMenu.this, AddNote.class));
                Intent intent = new Intent (MainMenu.this, AddNote.class);
                intent.putExtra("uid", uidTxt);
                intent.putExtra("correo", correoTxt);
                startActivity(intent);


            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, ShowNotes.class));
                Toast.makeText(MainMenu.this, "Listar Notas", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onStart(){
        loginManager();
        super.onStart();

    }

    private void loginManager(){
        if (user!=null){
            loadData();
        }else{
            startActivity(new Intent(MainMenu.this, MainActivity.class));
            finish();
        }
    }

    private void loadData(){
        usuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    // ocutar progress bar
                    progressBar.setVisibility(View.GONE);
                    // mostrar user info
                    userName.setVisibility(View.VISIBLE);
                    userMail.setVisibility(View.VISIBLE);
                    uid.setVisibility(View.VISIBLE);

                    // Datos
                    String nombre = ""+snapshot.child("nombre").getValue();
                    String correo = ""+snapshot.child("mail").getValue();
                    String uidText= ""+snapshot.child("uid").getValue();
                    //
                    userName.setText(nombre);
                    userMail.setText(correo);
                    uid.setText(uidText);

                    // Enable buttons

                    btnAdd.setEnabled(true);
                    btnList.setEnabled(true);
                    btnArchived.setEnabled(true);
                    btnProfile.setEnabled(true);
                    btnAbout.setEnabled(true);
                    btnLogout.setEnabled(true);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void logOut() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainMenu.this, MainActivity.class ));
        Toast.makeText(this, "log out realizado sin problemas", Toast.LENGTH_SHORT).show();

    }
}