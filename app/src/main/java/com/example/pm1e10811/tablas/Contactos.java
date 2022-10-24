package com.example.pm1e10811.tablas;

import java.sql.Blob;

public class Contactos{

    public Integer id;
    public String nombre;
    public String telefono;
    public String nota;

    // Constructor de clase
    public Contactos()
    {
        //Todo
    }

    public Contactos(Integer id, String nombre, String telefono, String nota) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nota = nota;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

}
