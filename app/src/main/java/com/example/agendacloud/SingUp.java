package com.example.agendacloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SingUp extends AppCompatActivity {
    EditText nombreEt, mailEt, contrasenaEt, contrasenaConfirmEt;
    Button btnSingup;
    TextView loginTxt;

    FirebaseAuth fireBaseAuth;
    ProgressDialog progressDialog;

    //
    String nombre = "", mail = "", contrasena = "", contrasenaConfirm = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Crear Cuenta");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        // Referencias
        nombreEt = findViewById(R.id.nombre_et);
        mailEt = findViewById(R.id.mail_et);
        contrasenaEt = findViewById(R.id.contrasena_et);
        contrasenaConfirmEt = findViewById(R.id.contrasena_confirm_et);
        btnSingup = findViewById(R.id.btn_singup);
        loginTxt = findViewById(R.id.login_txt);
        //
        fireBaseAuth = FirebaseAuth.getInstance();

        //
        progressDialog = new ProgressDialog(SingUp.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        // Event Hadlers
        btnSingup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // registrar user.
                validarEntradaUsuario();

            }
        });

        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent ( SingUp.this, Login.class));

            }
        });

    }

    private void validarEntradaUsuario(){
        nombre = nombreEt.getText().toString();
        mail = mailEt.getText().toString();
        contrasena = contrasenaEt.getText().toString();
        contrasenaConfirm = contrasenaConfirmEt.getText().toString();
        Log.i("info", "nombre: "+nombre);
        Log.i("info", "correo: "+mail);
        Log.i("info", "contrasena: "+contrasena);
        Log.i("info", "confirmar contrasena: "+contrasenaConfirm);




        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Campo nombre vacio", Toast.LENGTH_SHORT).show();

        }else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(this, "Correo: " + mail + " introducido no es correcto", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(contrasena)) {
            Toast.makeText(this, "Campo contrasena vacio", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(contrasenaConfirm)) {
            Toast.makeText(this, "Campo  repetir contrasena vacio", Toast.LENGTH_SHORT).show();
        }else if (!contrasena.equals(contrasenaConfirm)){
            Toast.makeText(this, "Debes confirmar la contrasena correctamente", Toast.LENGTH_SHORT).show();
        }else{
            CrearCuenta();
        }


    }

    private void CrearCuenta() {
        progressDialog.setMessage("Creando su cuenta...");
        progressDialog.show();

        // Crear usuario en Firebase
        fireBaseAuth.createUserWithEmailAndPassword(mail, contrasena).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //
                guardarUsuarioBD();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SingUp.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarUsuarioBD() {
        progressDialog.setMessage("Cuenta creada con exito");
        progressDialog.dismiss();


        String uid = fireBaseAuth.getUid();

        HashMap <String, String> Datos = new HashMap<>();
        Datos.put("uid", uid);
        Datos.put("mail", mail);
        Datos.put("nombre", nombre);
        Datos.put("contrasena", contrasena);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid)
                .setValue(Datos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(SingUp.this, "Cuenta Guardada con exito", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SingUp.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}