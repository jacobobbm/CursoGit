package com.example.agendacloud.Objetos;

public class Note {
    String idNote;
    String uidUsuario;
    String correoUsuario;
    String fechaHora;
    String titulo;
    String descripcion;
    String fechaNota;
    String estado;

    public Note(
        String idNote,
        String uidUsuario,
        String correoUsuario,
        String fechaHora,
        String titulo,
        String descripcion,
        String fechaNota,
        String estado ) {
        this.uidUsuario = uidUsuario;
        this.idNote = idNote;
        this.correoUsuario = correoUsuario;
        this.fechaHora = fechaHora;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaNota = fechaNota;
        this.estado = estado;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaNota() {
        return fechaNota;
    }

    public void setFechaNota(String fechaNota) {
        this.fechaNota = fechaNota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getIdNote() {
        return idNote;
    }

    public void setIdNote(String idNote) {
        this.idNote = idNote;
    }
}
