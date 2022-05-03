package com.example.agendacloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText emailLogin, contrasenaLogin;
    Button btnLogin;
    TextView singUpTextLogin;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    // Validacion
    String  correo = "", contrasena = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Crear Cuenta");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Inicializar vistas
        emailLogin = findViewById(R.id.mail_login);
        contrasenaLogin = findViewById(R.id.contrasena_login);
        btnLogin = findViewById(R.id.btn_login);
        singUpTextLogin = findViewById(R.id.singup_login_txt);

        //I
        progressDialog = new ProgressDialog(Login.this);
        firebaseAuth = FirebaseAuth.getInstance();

        // Gestores de eventos

        singUpTextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Login.this, SingUp.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarEntradaUsuario();
            }
        });



    }

    private void validarEntradaUsuario() {
        correo = emailLogin.getText().toString();
        contrasena = contrasenaLogin.getText().toString();

        // Validacion

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Correo Invalido", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(contrasena)){
            Toast.makeText(this, "Introduzca contrasena", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Login.this, "Datos validados", Toast.LENGTH_SHORT).show();

            loginUsuario();
        }
    }


    private void loginUsuario() {
        progressDialog.setMessage("Logging in... ");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(Login.this, MainMenu.class ));
                            Toast.makeText(Login.this, "Bienvenido(a): "+user.getEmail(), Toast.LENGTH_SHORT).show();

                            finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Usuario y/o contrasena incorrectos", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(Login.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}