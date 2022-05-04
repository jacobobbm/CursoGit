package com.example.agendacloud.AddNoteLogic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendacloud.Objetos.Note;
import com.example.agendacloud.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNote extends AppCompatActivity {


    TextView uidUsuario, correoUsuario, fechaHoraActual, fecha, estado;
    EditText titulo, descripcion;
    Button btnCalendario;
    int dia, mes, ano;

    DatabaseReference databaseReference;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        // Action var
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("f");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Init
        inicializarVariables();
        obtenerDatos();
        getFechaHoraActual();



        // Event Handler
        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                ano = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNote.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anoIntroducido, int mesIntroducido, int diaIntroducido) {
                        String diaFormateado, mesFormateado;
                        if (diaIntroducido < 10) {
                            diaFormateado = "0"+String.valueOf(diaIntroducido);

                        }else{
                            diaFormateado = String.valueOf(diaIntroducido);
                        }

                        int mes = mesIntroducido + 1;

                        if (mes < 10) {
                            mesFormateado = "0" + String.valueOf(mes);
                        }else{
                            mesFormateado = String.valueOf(mes);
                        }

                        fecha.setText(diaFormateado + "/" + mesFormateado + "/" + anoIntroducido);

                    }
                }
                , ano, mes, dia);

                datePickerDialog.show();
                
            }
        });
    }

    private void inicializarVariables(){
        uidUsuario = findViewById(R.id.uid_usuario);
        correoUsuario = findViewById(R.id.correo_usuario);
        fechaHoraActual = findViewById(R.id.fecha_hora_actual);
        fecha = findViewById(R.id.Fecha);
        estado = findViewById(R.id.Estado);
        titulo = findViewById(R.id.Titulo);
        descripcion = findViewById(R.id.Descripcion);
        btnCalendario = findViewById(R.id.btn_calendario);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void obtenerDatos(){
        // recuperar datos
        String correo_recuperado = getIntent().getStringExtra("correo");
        String uid_recuperado = getIntent().getStringExtra("uid");

        // Pasarlos a la UI
        uidUsuario.setText(uid_recuperado);
        correoUsuario.setText(correo_recuperado);

    }
    private void getFechaHoraActual(){
        String fechaHoraRegistro = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss",
                Locale.getDefault()).format(System.currentTimeMillis());
        fechaHoraActual.setText(fechaHoraRegistro);
    }

    private void guargarNota() {
        // obtener Datos
        String uidDb = uidUsuario.getText().toString();
        String correoDb = correoUsuario.getText().toString();
        String fechaHoraDb = fechaHoraActual.getText().toString();
        String fechaDb = fecha.getText().toString();
        String estadoDb = estado.getText().toString();
        String tituloDb = titulo.getText().toString();
        String descripcionDb = descripcion.getText().toString();


        // validar
        if (!uidDb.equals("") && !correoDb.equals("") && !fechaHoraDb.equals("") && !tituloDb.equals("") && !descripcionDb.equals("") && !fechaDb.equals("") && !estadoDb.equals("")) {
            // Crear obj nota
            Note note = new Note(
                    correoDb + "/" + fechaHoraDb,
                    uidDb,
                    correoDb,
                    fechaHoraDb,
                    tituloDb,
                    descripcionDb,
                    fechaDb,
                    estadoDb);

            String addedNote = databaseReference.push().getKey();
            String nombreBd = "Mis_Notas";

            databaseReference.child(nombreBd).child(addedNote).setValue(note);

            Toast.makeText(this, "Nota Guargada", Toast.LENGTH_SHORT).show();
            onBackPressed();

        } else {
            Toast.makeText(this, "Entrada Invalida", Toast.LENGTH_SHORT).show();
        }




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.menu_agenda, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
         switch (item.getItemId()){
             case R.id.Add_note:
                 guargarNota();
                 break;
         }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}