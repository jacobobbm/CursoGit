package com.example.agendacloud.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendacloud.R;

public class ViewHolderNote extends RecyclerView.ViewHolder {
    View mView;

    private ViewHolderNote.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick (View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickLitener(ViewHolderNote.ClickListener clickLitener){
        mClickListener = clickLitener;
    }
    public ViewHolderNote(@NonNull View itemView){
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getBindingAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view){
                mClickListener.onItemLongClick(view, getBindingAdapterPosition());
                return false;
            }
        });

    }

    public void recuperarDatos(Context context,
                               String idNote,
                               String uidUsuario,
                               String correoUsuario,
                               String fechaHoraReg,
                               String titulo,
                               String descripcion,
                               String fechaNota,
                               String estado ) {


        TextView id_nota_item, uid_usuario_item, correo_usuario_item, fecha_hora_reg_item;
        TextView Titulo_item, Descripcion_item, Fecha_item, Estado_item;

        id_nota_item = mView.findViewById(R.id.id_nota_item);
        uid_usuario_item = mView.findViewById(R.id.uid_usuario_item);
        correo_usuario_item = mView.findViewById(R.id.correo_usuario_item);
        fecha_hora_reg_item = mView.findViewById(R.id.fecha_hora_reg_item);
        Titulo_item = mView.findViewById(R.id.Titulo_item);
        Descripcion_item = mView.findViewById(R.id.Descripcion_item);
        Fecha_item = mView.findViewById(R.id.Fecha_item);
        Estado_item = mView.findViewById(R.id.Estado_item);

        //

        id_nota_item.setText(idNote);
        uid_usuario_item.setText(uidUsuario);
        correo_usuario_item.setText(correoUsuario);
        fecha_hora_reg_item.setText(fechaHoraReg);
        Titulo_item.setText(titulo);
        Descripcion_item.setText(descripcion);
        Fecha_item.setText(fechaNota);
        Estado_item.setText(estado);
    }
}
