package com.example.agendacloud.ShowNotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.example.agendacloud.Objetos.Note;
import com.example.agendacloud.R;
import com.example.agendacloud.ViewHolder.ViewHolderNote;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShowNotes extends AppCompatActivity {
    RecyclerView recyclerViewNotas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerAdapter<Note, ViewHolderNote> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Note> options;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mis_Notas");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        recyclerViewNotas = findViewById(R.id.recyclerViewNotas);
        recyclerViewNotas.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Mis_Notas");
        dialog = new Dialog(ShowNotes.this);


        showNoteList();


    }
    protected void onStart(){
        super.onStart();
        if (firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.startListening();
        }

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    private void showNoteList(){
        options = new FirebaseRecyclerOptions.Builder<Note>()
                .setQuery(databaseReference, Note.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Note, ViewHolderNote>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderNote noteHolder, int position, @NonNull Note note) {
                noteHolder.recuperarDatos(
                        getApplicationContext(),
                        note.getIdNote(),
                        note.getUidUsuario(),
                        note.getCorreoUsuario(),
                        note.getFechaHora(),
                        note.getTitulo(),
                        note.getDescripcion(),
                        note.getFechaNota(),
                        note.getEstado()
                );

            }


            @Override
            public ViewHolderNote onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_nota, parent, false);
                ViewHolderNote viewHolderNote = new ViewHolderNote(view);
                viewHolderNote.setOnClickLitener(new ViewHolderNote.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Toast.makeText(ShowNotes.this, "on item click", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                      Toast.makeText(ShowNotes.this, "on item long click", Toast.LENGTH_SHORT).show();
                        String idNote = getItem(position).getIdNote();

                        Button btnEliminar , btnEditar;
                        dialog.setContentView(R.layout.dialogo_opciones);

                        btnEditar = dialog.findViewById(R.id.editar_cd);
                        btnEliminar = dialog.findViewById(R.id.eliminar_cd);

                        btnEliminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                Toast.makeText(ShowNotes.this, "Nota Eliminada", Toast.LENGTH_SHORT).show();
                                EliminarNota(idNote);
                                dialog.dismiss();
                            }

                        });

                        btnEditar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(ShowNotes.this, "Nota editada", Toast.LENGTH_SHORT).show();

                            }
                        });
                        dialog.show();
                    }
                });
                return viewHolderNote;

            }
        };

        linearLayoutManager = new LinearLayoutManager(ShowNotes.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerViewNotas.setLayoutManager(linearLayoutManager);
        recyclerViewNotas.setAdapter(firebaseRecyclerAdapter);

    }

    private void EliminarNota(String idNote) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowNotes.this);
        builder.setTitle("Eliminar Nota");
        builder.setMessage("Deseas eliminar la nota seleccionada?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // consulta
                Query query = databaseReference.orderByChild("idNote").equalTo(idNote);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(ShowNotes.this, "Nota eliminada", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ShowNotes.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ShowNotes.this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();

    }
}