package com.example.pm1e10811.tablas;

public class Transacciones
{
    //Nombre de la base de datos
    public static final String NameDatabase = "PM1E1DB";

    /* Creacion de las tablas de la BD */
    public static final String TbContactos = "contactos";

    /* Campos de la tabla personas */
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String nota = "nota";


    // DDL
    public static final String CTContactos = "CREATE TABLE contactos (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " nombre TEXT, telefono TEXT, nota TEXT)";

    public static final String GetContactos = "SELECT * FROM " + Transacciones.TbContactos;

    public static final String DropTContactos = "DROP TABLE IF EXISTS contactos";

}
